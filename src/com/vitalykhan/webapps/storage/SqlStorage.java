package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.ContactType;
import com.vitalykhan.webapps.model.Resume;
import com.vitalykhan.webapps.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @FunctionalInterface
    private interface ABlockOfCode<T> {
        T executeSpecific(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    private interface SqlTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }

    private <T> T executeSql(ABlockOfCode<T> aBlockOfCode, String sql, String... parameters) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setString(i + 1, parameters[i]);
            }
            return aBlockOfCode.executeSpecific(ps);
        } catch (SQLException e) {
            throw new StorageException("SQL Storage exception", "", e);
        }
    }

    private <T> T executeTransaction(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new StorageException("Transactional Exception", "", e);
        }
    }

    @Override
    public void clear() {
        executeSql(ps -> {
            ps.execute();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        executeTransaction(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
                ps.setString(1, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new ResumeDoesntExistInStorageException(r.getUuid());
                }
            }
            return doSave(r, connection);
        });
    }

    @Override
    public void save(Resume r) {
        executeTransaction(connection -> doSave(r, connection));
    }

    private Object doSave(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO resume(uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ResumeExistsInStorageException(r.getUuid());
        }
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact(resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContactsMap().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return null;
    }

    @Override
    public Resume get(String uuid) {
        return executeSql(ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new ResumeDoesntExistInStorageException(uuid);
                    }
                    Resume result = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String type = rs.getString("type");
                        if (type == null) {
                            break;
                        }
                        result.addContact(ContactType.valueOf(type),
                                rs.getString("value"));
                    } while (rs.next());
                    return result;
                },
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid=c.resume_uuid " +
                        "WHERE r.uuid=?",
                uuid);
    }

    @Override
    public void delete(String uuid) {
        executeSql(ps -> {
                    if (ps.executeUpdate() == 0) {
                        throw new ResumeDoesntExistInStorageException(uuid);
                    }
                    return null;
                },
                "DELETE FROM resume WHERE uuid=?",
                uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        //TODO These 2 queries below must be enclosed into single transaction

        List<Resume> resumeList = executeSql(ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        }, "SELECT * FROM resume r ORDER BY full_name, uuid");

        Map<String, Map<ContactType, String>> contactMap = executeSql(ps -> {
            Map<String, Map<ContactType, String>> contacts = new HashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resume_uuid = rs.getString("resume_uuid");
                Map<ContactType, String> currentMap = contacts.get(resume_uuid);
                if (currentMap == null) {
                    contacts.put(resume_uuid, new HashMap<>());
                    currentMap = contacts.get(resume_uuid);
                }
                currentMap.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return contacts;
        }, "SELECT * FROM contact");
        for (Resume resume : resumeList) {
            Map<ContactType, String> currentMap = contactMap.get(resume.getUuid());
            if (currentMap != null) {
                for (Map.Entry<ContactType, String> entry : currentMap.entrySet()) {
                    resume.addContact(entry.getKey(), entry.getValue());
                }
            }
        }
        return resumeList;
    }

    @Override
    public int size() {
        return executeSql(ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }, "SELECT COUNT(*) FROM resume");
    }
}
