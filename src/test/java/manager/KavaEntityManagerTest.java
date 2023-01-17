package manager;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kava.barattolo.config.ConnectionConfig;
import org.kava.barattolo.config.ManagedClassesConfig;
import org.kava.barattolo.manager.KavaEntityManager;
import org.kava.lungo.Level;
import utils.ComplexTestEntity;
import utils.DatabaseAbility;
import utils.SimpleTestEntity;

import java.sql.Date;
import java.util.List;

public class KavaEntityManagerTest extends DatabaseAbility {
    private final ConnectionConfig connectionConfig = new ConnectionConfig(
            TEST_URL_PROPERTY, TEST_USERNAME_PROPERTY, TEST_PASSWORD_PROPERTY
    );
    private final ManagedClassesConfig managedClassesConfig = new ManagedClassesConfig(TEST_MANAGED_CLASSES);
    private final EntityManager entityManager = new KavaEntityManager(connectionConfig, managedClassesConfig, Level.DEBUG);

    SimpleTestEntity simpleTestEntity1 = new SimpleTestEntity(1L, "name1");
    SimpleTestEntity simpleTestEntity2 = new SimpleTestEntity(2L, "name2");
    ComplexTestEntity complexTestEntity1 = new ComplexTestEntity("name1", "lastName1", 11L, 1, Date.valueOf("2022-11-11"), simpleTestEntity1);
    ComplexTestEntity complexTestEntity2 = new ComplexTestEntity("name2", "lastName2", 22L, 2, Date.valueOf("2022-11-22"), simpleTestEntity2);

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
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(simpleTestEntity2, results.get(0));
    }

    @Test
    public void testComplexRemove() {
        // given
        addToDatabase(simpleTestEntity1);
        addToDatabase(simpleTestEntity2);
        addToDatabase(complexTestEntity1);
        addToDatabase(complexTestEntity2);

        // when
        entityManager.remove(complexTestEntity1);

        // then
        List<ComplexTestEntity> results = getComplexEntitiesFromDatabase();
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(complexTestEntity2, results.get(0));
    }

    @Test
    public void testSimplePersist() {
        // given
        assert getSimpleEntitiesFromDatabase().size() == 0;

        // when
        entityManager.persist(simpleTestEntity1);

        // then
        List<SimpleTestEntity> results = getSimpleEntitiesFromDatabase();
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(simpleTestEntity1, results.get(0));
    }

    @Test
    public void testComplexPersist() {
        // given
        assert getComplexEntitiesFromDatabase().size() == 0;

        // when
        entityManager.persist(complexTestEntity1);

        // then
        List<ComplexTestEntity> results = getComplexEntitiesFromDatabase();
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(complexTestEntity1, results.get(0));
    }

    @Test
    public void testSimpleFind() {
        // given
        addToDatabase(simpleTestEntity1);
        addToDatabase(simpleTestEntity2);

        // when
        SimpleTestEntity foundEntity1 = entityManager.find(SimpleTestEntity.class, 1L);
        SimpleTestEntity foundEntity2 = entityManager.find(SimpleTestEntity.class, 2L);

        // then
        Assertions.assertEquals(simpleTestEntity1, foundEntity1);
        Assertions.assertEquals(simpleTestEntity2, foundEntity2);
    }

    @Test
    public void testComplexFind() {
        // given
        addToDatabase(simpleTestEntity1);
        addToDatabase(simpleTestEntity2);
        addToDatabase(complexTestEntity1);
        addToDatabase(complexTestEntity2);

        // when
        ComplexTestEntity foundEntity1 = entityManager.find(ComplexTestEntity.class, "name1");
        ComplexTestEntity foundEntity2 = entityManager.find(ComplexTestEntity.class, "name2");

        // then
        Assertions.assertEquals(complexTestEntity1, foundEntity1);
        Assertions.assertEquals(complexTestEntity2, foundEntity2);

        System.out.println("After assertions");
        System.out.println(foundEntity1.getSimpleTestEntity().getName());
        System.out.println(foundEntity1.getSimpleTestEntity().getId());
    }
}
