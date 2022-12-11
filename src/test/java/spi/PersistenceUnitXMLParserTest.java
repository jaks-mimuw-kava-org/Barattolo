package spi;

import jakarta.persistence.spi.PersistenceUnitInfo;
import org.junit.jupiter.api.Test;
import org.kava.barattolo.spi.util.xml.PersistenceUnitXMLParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceUnitXMLParserTest {
    @Test
    public void testCanParsePersistenceXmlFile() {
        // given
        String persistenceFilePath = "src/test/resources/META-INF/persistence.xml";
        String persistenceUnitName = "org.kava.test";

        // when
        PersistenceUnitInfo persistenceUnitInfo = PersistenceUnitXMLParser
                .parse(persistenceFilePath, persistenceUnitName);

        // then
        assertEquals(persistenceUnitName, persistenceUnitInfo.getPersistenceUnitName());
        assertEquals(1, persistenceUnitInfo.getManagedClassNames().size());
        assertEquals("utils.SimpleTestEntity", persistenceUnitInfo.getManagedClassNames().get(0));
        assertEquals("org.postgresql.Driver", persistenceUnitInfo.getProperties().getProperty("jakarta.persistence.jdbc.driver"));
        assertEquals("jdbc:postgresql://localhost/test", persistenceUnitInfo.getProperties().getProperty("jakarta.persistence.jdbc.url"));
        assertEquals("admin", persistenceUnitInfo.getProperties().getProperty("jakarta.persistence.jdbc.user"));
        assertEquals("admin", persistenceUnitInfo.getProperties().getProperty("jakarta.persistence.jdbc.password"));
    }
}
