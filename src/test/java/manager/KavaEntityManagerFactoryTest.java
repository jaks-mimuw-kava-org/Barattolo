package manager;

import com.kava.manager.KavaEntityManagerFactory;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KavaEntityManagerFactoryTest {
    @Test
    public void testCreate() {
        // when
        EntityManager entityManager = new KavaEntityManagerFactory().createEntityManager();

        // then
        assertNotNull(entityManager);
    }
}
