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
            Connection connection = DriverManager.getConnection(
                    getTestUrl(), getTestUsername(), getTestPassword()
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addToDatabase(TestEntity testEntity) {
        String query = "INSERT INTO TestEntity(id, name) VALUES (%d, '%s')"
                .formatted(testEntity.getId(), testEntity.getName());
        try {
            Connection connection = DriverManager.getConnection(
                    getTestUrl(), getTestUsername(), getTestPassword()
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<TestEntity> getFromDatabase() {
        String query = "SELECT * FROM TestEntity";
        List<TestEntity> entities = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    getTestUrl(), getTestUsername(), getTestPassword()
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TestEntity entity = new TestEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }
}
