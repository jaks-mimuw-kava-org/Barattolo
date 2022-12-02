package manager;

import jakarta.persistence.EntityManager;
import org.kava.barattolo.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KavaEntityManagerFactoryTest {
    @Test
    public void testCreate() {
        // when
        EntityManager entityManager = new KavaEntityManagerFactory(driverConfig, connectionConfig, persistenceConfig).createEntityManager();

        // then
        assertNotNull(entityManager);
    }
}
