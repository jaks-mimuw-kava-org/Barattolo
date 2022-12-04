package org.kava.barattolo.spi.util;

import jakarta.persistence.spi.PersistenceUnitInfo;
import org.kava.barattolo.spi.KavaPersistenceUnitInfo;
import org.kava.barattolo.spi.KavaPersistenceUnitProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KavaPersistenceUnitInfoBuilder {
    private String persistenceUnitName;
    private List<String> managedClassNames;
    private Properties properties;

    public KavaPersistenceUnitInfoBuilder(String persistenceUnitName, List<String> managedClassNames,
                                          Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

    public static KavaPersistenceUnitInfoBuilder builder() {
        return new KavaPersistenceUnitInfoBuilder(null, new ArrayList<>(), new Properties());
    }

    public static KavaPersistenceUnitInfoBuilder builder(PersistenceUnitInfo persistenceUnitInfo) {
        return new KavaPersistenceUnitInfoBuilder(
                persistenceUnitInfo.getPersistenceUnitName(),
                persistenceUnitInfo.getManagedClassNames(),
                persistenceUnitInfo.getProperties()
        );
    }

    public KavaPersistenceUnitInfoBuilder withProperty(KavaPersistenceUnitProperty property, Object propertyValue) {
        this.properties.put(property.name, propertyValue);
        return this;
    }

    public KavaPersistenceUnitInfo build() {
        return new KavaPersistenceUnitInfo(persistenceUnitName, managedClassNames, properties);
    }
}
