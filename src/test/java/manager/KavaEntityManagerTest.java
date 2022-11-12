package manager;

import com.kava.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseAbility;
import utils.TestEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KavaEntityManagerTest extends DatabaseAbility {
    private final EntityManager entityManager = new KavaEntityManagerFactory().createEntityManager(
            Map.of("url", getTestUrl(),
                    "username", getTestUsername(),
                    "password", getTestPassword())
    );

    @AfterEach
    void cleanUp() {
        cleanTable("TestEntity");
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
        assert Objects.equals(results.get(0).getId(), testEntity2.getId());
        assert Objects.equals(results.get(0).getName(), testEntity2.getName());
    }

    @Test
    public void testPersist() {
        // given
        assert getFromDatabase().size() == 0;
        TestEntity testEntity1 = new TestEntity(1L, "name1");

        // when
        entityManager.persist(testEntity1);

        // then
        List<TestEntity> results = getFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0).getId(), testEntity1.getId());
        assert Objects.equals(results.get(0).getName(), testEntity1.getName());
    }
}
