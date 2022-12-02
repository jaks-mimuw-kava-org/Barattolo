package org.kava.barattolo.spi.util;

import jakarta.persistence.spi.PersistenceUnitInfo;
import org.kava.barattolo.spi.KavaPersistenceUnitInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.kava.barattolo.spi.KavaPersistenceUnitProperties.*;

public class KavaPersistenceUnitInfoBuilder {
    private String persistenceUnitName;
    private List<String> managedClassNames;
    private String driverClass;
    private String url;
    private String user;
    private String password;

    public KavaPersistenceUnitInfoBuilder(String persistenceUnitName, List<String> managedClassNames,
                                          String driverClass, String url, String user, String password) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.driverClass = driverClass;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static KavaPersistenceUnitInfoBuilder builder() {
        return new KavaPersistenceUnitInfoBuilder(
                null, new ArrayList<>(),
                null, null, null, null
        );
    }

    public static KavaPersistenceUnitInfoBuilder builder(PersistenceUnitInfo persistenceUnitInfo) {
        return new KavaPersistenceUnitInfoBuilder(
                persistenceUnitInfo.getPersistenceUnitName(),
                persistenceUnitInfo.getManagedClassNames(),
                persistenceUnitInfo.getProperties().getProperty(DRIVER_CLASS_PROPERTY_NAME),
                persistenceUnitInfo.getProperties().getProperty(URL_PROPERTY_NAME),
                persistenceUnitInfo.getProperties().getProperty(USER_PROPERTY_NAME),
                persistenceUnitInfo.getProperties().getProperty(PASSWORD_PROPERTY_NAME)
        );
    }

    public KavaPersistenceUnitInfoBuilder withDriverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public KavaPersistenceUnitInfoBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public KavaPersistenceUnitInfoBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public KavaPersistenceUnitInfoBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public KavaPersistenceUnitInfo build() {
        Properties properties = buildProperties();
        return new KavaPersistenceUnitInfo(persistenceUnitName, managedClassNames, properties);
    }

    private Properties buildProperties() {
        Properties properties = new Properties();
        properties.setProperty(DRIVER_CLASS_PROPERTY_NAME, driverClass);
        properties.setProperty(URL_PROPERTY_NAME, url);
        properties.setProperty(USER_PROPERTY_NAME, user);
        properties.setProperty(PASSWORD_PROPERTY_NAME, password);

        return properties;
    }
}
