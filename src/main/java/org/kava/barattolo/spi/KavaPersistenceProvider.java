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
import org.kava.barattolo.spi.util.PersistenceUnitXMLParser;

import java.util.Map;

import static org.kava.barattolo.spi.KavaPersistenceUnitProperties.*;

public class KavaPersistenceProvider implements PersistenceProvider {
    @Override
    public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
        PersistenceUnitXMLParser parser = new PersistenceUnitXMLParser();
        PersistenceUnitInfo persistenceUnitInfo = parser.parse(emName);

        PersistenceUnitInfo overriddenPersistenceUnitInfo = overrideProperties(persistenceUnitInfo, map);


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
                                                   Map<String, String> overrideProperties) {
        KavaPersistenceUnitInfoBuilder builder = KavaPersistenceUnitInfoBuilder.builder(persistenceUnitInfo);
        if (overrideProperties.containsKey(DRIVER_CLASS_PROPERTY_NAME)) {
            builder = builder.withDriverClass(overrideProperties.get(DRIVER_CLASS_PROPERTY_NAME));
        }
        if (overrideProperties.containsKey(URL_PROPERTY_NAME)) {
            builder = builder.withUrl(overrideProperties.get(URL_PROPERTY_NAME));
        }
        if (overrideProperties.containsKey(USER_PROPERTY_NAME)) {
            builder = builder.withUser(overrideProperties.get(USER_PROPERTY_NAME));
        }
        if (overrideProperties.containsKey(PASSWORD_PROPERTY_NAME)) {
            builder = builder.withPassword(overrideProperties.get(PASSWORD_PROPERTY_NAME));
        }
        return builder.build();
    }

    private KavaEntityManagerFactory createFactory(PersistenceUnitInfo persistenceUnitInfo) {
        DriverConfig driverConfig = new DriverConfig(
                persistenceUnitInfo.getProperties().getProperty(DRIVER_CLASS_PROPERTY_NAME)
        );
        ConnectionConfig connectionConfig = new ConnectionConfig(
                persistenceUnitInfo.getProperties().getProperty(URL_PROPERTY_NAME),
                persistenceUnitInfo.getProperties().getProperty(USER_PROPERTY_NAME),
                persistenceUnitInfo.getProperties().getProperty(PASSWORD_PROPERTY_NAME)
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
