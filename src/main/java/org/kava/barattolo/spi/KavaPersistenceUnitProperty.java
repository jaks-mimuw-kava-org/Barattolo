package org.kava.barattolo.spi;

public enum KavaPersistenceUnitProperty {
    URL_PROPERTY_NAME("jakarta.persistence.jdbc.url"),
    USER_PROPERTY_NAME("jakarta.persistence.jdbc.user"),
    PASSWORD_PROPERTY_NAME("jakarta.persistence.jdbc.password"),
    DRIVER_CLASS_PROPERTY_NAME("jakarta.persistence.jdbc.driver"),
    LOGGER_LEVEL_PROPERTY_NAME("org.kava.barattolo.logger.level");

    public final String name;

    private KavaPersistenceUnitProperty(String name) {
        this.name = name;
    }
}
