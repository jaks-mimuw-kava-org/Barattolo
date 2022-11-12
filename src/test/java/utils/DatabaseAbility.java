package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAbility {
    private static final String TEST_URL_PROPERTY =
            "jdbc:tc:postgresql:14.6:///test?TC_INITSCRIPT=file:src/test/resources/init_postgres.sql";
    private static final String TEST_USERNAME_PROPERTY = "admin";
    private static final String TEST_PASSWORD_PROPERTY = "admin";

    protected String getTestUrl() {
        return TEST_URL_PROPERTY;
    }

    protected String getTestUsername() {
        return TEST_USERNAME_PROPERTY;
    }

    protected String getTestPassword() {
        return TEST_PASSWORD_PROPERTY;
    }

    protected void cleanTable(String tableName) {
        String query = "DELETE FROM %s".formatted(tableName);
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addToDatabase(SimpleTestEntity simpleTestEntity) {
        String query = "INSERT INTO SimpleTestEntity(id, name) VALUES (%d, '%s')"
                .formatted(simpleTestEntity.getId(), simpleTestEntity.getName());
        addToDatabase(query);
    }

    protected void addToDatabase(ComplexTestEntity complexTestEntity) {
        String query = ("INSERT INTO ComplexTestEntity(name, lastName, height, age, lastLogin) " +
                "VALUES ('%s', '%s', %d, %d, '%s')")
                .formatted(complexTestEntity.getName(), complexTestEntity.getLastName(), complexTestEntity.getHeight(),
                        complexTestEntity.getAge(), complexTestEntity.getLastLogin().toString());
        addToDatabase(query);
    }

    private void addToDatabase(String query) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<SimpleTestEntity> getSimpleEntitiesFromDatabase() {
        String query = "SELECT * FROM SimpleTestEntity";
        List<SimpleTestEntity> entities = new ArrayList<>();

        try {
            ResultSet resultSet = getFromDatabase(query);
            while (resultSet.next()) {
                SimpleTestEntity entity = new SimpleTestEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );

                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }

    protected List<ComplexTestEntity> getComplexEntitiesFromDatabase() {
        String query = "SELECT * FROM ComplexTestEntity";
        List<ComplexTestEntity> entities = new ArrayList<>();

        try {
            ResultSet resultSet = getFromDatabase(query);
            while (resultSet.next()) {
                ComplexTestEntity entity = new ComplexTestEntity(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getLong("height"),
                        resultSet.getInt("age"),
                        resultSet.getDate("lastLogin")
                );

                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }

    private ResultSet getFromDatabase(String query) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                getTestUrl(), getTestUsername(), getTestPassword()
        );
    }
}
