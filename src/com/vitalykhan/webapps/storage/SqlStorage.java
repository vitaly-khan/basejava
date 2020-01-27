package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.*;
import com.vitalykhan.webapps.sql.ConnectionFactory;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class not found", e);
        }
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
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section(resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : r.getSectionsMap().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());

                Section section = entry.getValue();
                String value = null;
                if (section instanceof StringSection) {
                    value = ((StringSection) section).getContent();
                } else if (section instanceof ListSection) {
                    value = ((ListSection) section).getItems().stream()
                            .reduce("", (s, str) -> s.concat(str).concat("\n"));
                    value = value.substring(0, value.length() - 1);
                }
                ps.setString(3, value);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return null;
    }

    @Override
    public Resume get(String uuid) {

        return executeTransaction(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?");
                 PreparedStatement psContacts = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?");
                 PreparedStatement psSections = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {

                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new ResumeDoesntExistInStorageException(uuid);
                }
                Resume result = new Resume(uuid, rs.getString("full_name"));

                //Reading and adding contacts
                psContacts.setString(1, uuid);
                ResultSet rsSections = psContacts.executeQuery();
                while (rsSections.next()) {
                    String cType = rsSections.getString("type");
                    // No NPE check because of DB structure
                    result.addContact(ContactType.valueOf(cType), rsSections.getString("value"));
                }

                //Reading and adding sections
                psSections.setString(1, uuid);
                rsSections = psSections.executeQuery();

                while (rsSections.next()) {
                    createAndAddSection(rsSections, result);
                }
                return result;
            }
        });
    }

    private void createAndAddSection(ResultSet rs, Resume resume) throws SQLException {
        Section section = null;
        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                section = new StringSection(rs.getString("value"));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new ListSection(new ArrayList<>(Arrays.asList(
                        rs.getString("value").split("\n"))));
                break;
            default:
                throw new IllegalStateException("Unknown Section Type");
        }
        resume.addSection(type, section);
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

        return executeTransaction(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid");
                 PreparedStatement psContacts = connection.prepareStatement("SELECT * FROM contact");
                 PreparedStatement psSections = connection.prepareStatement("SELECT * FROM section")) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }

                ResultSet rsContacts = psContacts.executeQuery();
                while (rsContacts.next()) {
                    resumes.get(rsContacts.getString("resume_uuid"))
                            .addContact(ContactType.valueOf(rsContacts.getString("type")),
                            rsContacts.getString("value"));
                }

                ResultSet rsSections = psSections.executeQuery();
                while (rsSections.next()) {

                    createAndAddSection(rsSections, resumes.get(rsSections.getString("resume_uuid")));
                }

                return new ArrayList<>(resumes.values());
            }
        });
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
