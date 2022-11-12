package manager;

import com.kava.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.ComplexTestEntity;
import utils.DatabaseAbility;
import utils.SimpleTestEntity;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KavaEntityManagerTest extends DatabaseAbility {
    private final EntityManager entityManager = new KavaEntityManagerFactory().createEntityManager(
            Map.of("url", getTestUrl(),
                    "username", getTestUsername(),
                    "password", getTestPassword())
    );

    SimpleTestEntity simpleTestEntity1 = new SimpleTestEntity(1L, "name1");
    SimpleTestEntity simpleTestEntity2 = new SimpleTestEntity(2L, "name2");
    ComplexTestEntity complexTestEntity1 = new ComplexTestEntity("name1", "lastName1", 11L, 1, Date.valueOf("2022-11-11"));
    ComplexTestEntity complexTestEntity2 = new ComplexTestEntity("name2", "lastName2", 22L, 2, Date.valueOf("2022-11-22"));

    @AfterEach
    void cleanUp() {
        cleanTable("SimpleTestEntity");
        cleanTable("ComplexTestEntity");
    }

    @Test
    public void testSimpleRemove() {
        // given
        addToDatabase(simpleTestEntity1);
        addToDatabase(simpleTestEntity2);

        // when
        entityManager.remove(simpleTestEntity1);

        // then
        List<SimpleTestEntity> results = getSimpleEntitiesFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0), simpleTestEntity2);
    }

    @Test
    public void testComplexRemove() {
        // given
        addToDatabase(complexTestEntity1);
        addToDatabase(complexTestEntity2);

        // when
        entityManager.remove(complexTestEntity1);

        // then
        List<ComplexTestEntity> results = getComplexEntitiesFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0), complexTestEntity2);
    }

    @Test
    public void testSimplePersist() {
        // given
        assert getSimpleEntitiesFromDatabase().size() == 0;

        // when
        entityManager.persist(simpleTestEntity1);

        // then
        List<SimpleTestEntity> results = getSimpleEntitiesFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0), simpleTestEntity1);
    }

    @Test
    public void testComplexPersist() {
        // given
        assert getComplexEntitiesFromDatabase().size() == 0;

        // when
        entityManager.persist(complexTestEntity1);

        // then
        List<ComplexTestEntity> results = getComplexEntitiesFromDatabase();
        assert results.size() == 1;
        assert Objects.equals(results.get(0), complexTestEntity1);
    }
}
