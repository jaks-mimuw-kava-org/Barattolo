package manager;

import com.kava.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.TestEntity;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KavaEntityManagerTest {
    private static final String TEST_URL_PROPERTY = "jdbc:postgresql://localhost/test";
    private static final String TEST_USERNAME_PROPERTY = "admin";
    private static final String TEST_PASSWORD_PROPERTY = "admin";

    private final EntityManager entityManager = new KavaEntityManagerFactory().createEntityManager(
            Map.of("url", TEST_URL_PROPERTY,
                    "username", TEST_USERNAME_PROPERTY,
                    "password", TEST_PASSWORD_PROPERTY)
    );

    @AfterEach
    void cleanUp() {
        String query = "DELETE FROM TestEntity";
        try {
            Connection connection = DriverManager.getConnection(
                    TEST_URL_PROPERTY, TEST_USERNAME_PROPERTY, TEST_PASSWORD_PROPERTY
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemove() {
        // given
        TestEntity testEntity1 = new TestEntity(1L, "name1");
        TestEntity testEntity2 = new TestEntity(2L, "name2");

        addToDatabase(testEntity1);
        addToDatabase(testEntity2);

        // when
        entityManager.remove(testEntity1);

        // then
        List<TestEntity> results = getFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0).id, testEntity2.id);
        assert Objects.equals(results.get(0).name, testEntity2.name);
    }

    private void addToDatabase(TestEntity testEntity) {
        String query = "INSERT INTO TestEntity(id, name) VALUES (%d, '%s')"
                .formatted(testEntity.getId(), testEntity.getName());
        try {
            Connection connection = DriverManager.getConnection(
                    TEST_URL_PROPERTY, TEST_USERNAME_PROPERTY, TEST_PASSWORD_PROPERTY
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TestEntity> getFromDatabase() {
        String query = "SELECT * FROM TestEntity";
        List<TestEntity> entities = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    TEST_URL_PROPERTY, TEST_USERNAME_PROPERTY, TEST_PASSWORD_PROPERTY
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
