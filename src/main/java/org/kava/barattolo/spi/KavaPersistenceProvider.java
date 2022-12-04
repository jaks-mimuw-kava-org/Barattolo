package org.kava.barattolo.spi;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.ProviderUtil;
import org.kava.barattolo.config.ConnectionConfig;
import org.kava.barattolo.config.DriverConfig;
import org.kava.barattolo.config.ManagedClassesConfig;
import org.kava.barattolo.manager.KavaEntityManagerFactory;
import org.kava.barattolo.spi.util.KavaPersistenceUnitInfoBuilder;
import org.kava.barattolo.spi.util.xml.PersistenceUnitXMLParser;

import java.util.Map;

import static org.kava.barattolo.spi.KavaPersistenceUnitProperty.*;

public class KavaPersistenceProvider implements PersistenceProvider {
    private static final String PERSISTENCE_FILE_PATH = "META_INF/persistence.xml";

    @Override
    public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
        PersistenceUnitInfo fileBasedPersistenceUnitInfo = PersistenceUnitXMLParser.parse(PERSISTENCE_FILE_PATH, emName);
        PersistenceUnitInfo overriddenPersistenceUnitInfo = overrideProperties(fileBasedPersistenceUnitInfo, map);

        return createFactory(overriddenPersistenceUnitInfo);
    }

    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateSchema(PersistenceUnitInfo info, Map map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateSchema(String persistenceUnitName, Map map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProviderUtil getProviderUtil() {
        throw new UnsupportedOperationException();
    }

    private PersistenceUnitInfo overrideProperties(PersistenceUnitInfo persistenceUnitInfo,
                                                   Map<String, Object> overrideProperties) {
        KavaPersistenceUnitInfoBuilder builder = KavaPersistenceUnitInfoBuilder.builder(persistenceUnitInfo);
        for (KavaPersistenceUnitProperty property : KavaPersistenceUnitProperty.values()) {
            if (overrideProperties.containsKey(property.name)) {
                builder = builder.withProperty(property, overrideProperties.get(property.name));
            }
        }

        return builder.build();
    }

    private KavaEntityManagerFactory createFactory(PersistenceUnitInfo persistenceUnitInfo) {
        DriverConfig driverConfig = new DriverConfig(
                persistenceUnitInfo.getProperties().getProperty(DRIVER_CLASS_PROPERTY_NAME.name)
        );
        ConnectionConfig connectionConfig = new ConnectionConfig(
                persistenceUnitInfo.getProperties().getProperty(URL_PROPERTY_NAME.name),
                persistenceUnitInfo.getProperties().getProperty(USER_PROPERTY_NAME.name),
                persistenceUnitInfo.getProperties().getProperty(PASSWORD_PROPERTY_NAME.name)
        );
        ManagedClassesConfig managedClassesConfig = new ManagedClassesConfig(
                persistenceUnitInfo.getManagedClassNames()
        );

        return new KavaEntityManagerFactory(
                driverConfig,
                connectionConfig,
                managedClassesConfig
        );
    }
}
