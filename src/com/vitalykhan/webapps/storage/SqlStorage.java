package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.ContactType;
import com.vitalykhan.webapps.model.Resume;
import com.vitalykhan.webapps.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
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
        this.<Void>executeSql(ps -> {
                    if (ps.executeUpdate() == 0) {
                        throw new ResumeDoesntExistInStorageException(r.getUuid());
                    }
                    return null;
                },
                "UPDATE resume SET full_name=? WHERE uuid=?",
                r.getFullName(),
                r.getUuid());
    }

    @Override
    public void save(Resume r) {
        executeTransaction(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume(uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new ResumeExistsInStorageException(r.getUuid());
            }
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO contact(resume_uuid, type, value) VALUES (?,?,?)");
            for (Map.Entry<ContactType, String> entry : r.getContactsMap().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            return null;
        });

//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statementInsert = connection.prepareStatement(
//                     "INSERT INTO resume(uuid, full_name) VALUES (?,?)");
//             PreparedStatement statementSelect = connection.prepareStatement(
//                     "SELECT * FROM resume WHERE uuid = ?")) {
//            statementSelect.setString(1, r.getUuid());
//            if (!statementSelect.executeQuery().next()) {//check given Resume doesn't exist
//                statementInsert.setString(1, r.getUuid());
//                statementInsert.setString(2, r.getFullName());
//                statementInsert.executeUpdate();
//            } else {
//                throw new ResumeExistsInStorageException(r.getUuid());
//            }
//        } catch (SQLException e) {
//            throw new StorageException("SQL Storage exception", "", e);
//        }
    }

    @Override
    public Resume get(String uuid) {
        return executeSql(ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new ResumeDoesntExistInStorageException(uuid);
                    }
                    Resume result = new Resume(uuid, rs.getString("full_name"));
//                    do {
//                        result.addContact(ContactType.valueOf(rs.getString("type")),
//                                rs.getString("value"));
//                    } while (rs.next());
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
        return executeSql(ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumeList;
        }, "SELECT * FROM resume ORDER BY full_name, uuid");
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
