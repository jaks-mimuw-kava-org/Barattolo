package manager;

import jakarta.persistence.EntityManager;
import org.kava.barattolo.config.ConnectionConfig;
import org.kava.barattolo.config.DriverConfig;
import org.kava.barattolo.config.ManagedClassesConfig;
import org.kava.barattolo.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.kava.lungo.Level;
import utils.DatabaseAbility;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KavaEntityManagerFactoryTest extends DatabaseAbility {
    @Test
    public void testCreate() {
        // given
        DriverConfig driverConfig = new DriverConfig(TEST_DRIVER_CLASS);
        ConnectionConfig connectionConfig = new ConnectionConfig(
                TEST_URL_PROPERTY, TEST_USERNAME_PROPERTY, TEST_PASSWORD_PROPERTY
        );
        ManagedClassesConfig managedClassesConfig = new ManagedClassesConfig(TEST_MANAGED_CLASSES);

        // when
        EntityManager entityManager = new KavaEntityManagerFactory(
                driverConfig,
                connectionConfig,
                managedClassesConfig,
                Level.DEBUG
        )
                .createEntityManager();

        // then
        assertNotNull(entityManager);
    }
}
