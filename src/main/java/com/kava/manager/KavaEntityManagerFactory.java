package com.kava.manager;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

public class KavaEntityManagerFactory implements EntityManagerFactory {
    private static final String DRIVER_CLASS_PROPERTY = "driver_class";
    private static final String URL_PROPERTY = "url";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    private boolean isOpen = true;

    @Override
    public EntityManager createEntityManager() {
        return createEntityManager(Map.of());
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        throwIfClosed();

        ConnectionConfig defaultConnectionConfig = getDefaultConfig();
        ConnectionConfig finalConnectionConfig = new ConnectionConfig(
                map.getOrDefault(DRIVER_CLASS_PROPERTY, defaultConnectionConfig.driverClass()).toString(),
                map.getOrDefault(URL_PROPERTY, defaultConnectionConfig.url()).toString(),
                map.getOrDefault(USERNAME_PROPERTY, defaultConnectionConfig.username()).toString(),
                map.getOrDefault(PASSWORD_PROPERTY, defaultConnectionConfig.password()).toString()
        );

        return new KavaEntityManager(finalConnectionConfig);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Metamodel getMetamodel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void close() {
        isOpen = false;
    }

    @Override
    public Map<String, Object> getProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache getCache() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addNamedQuery(String name, Query query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        throw new UnsupportedOperationException();
    }

    private ConnectionConfig getDefaultConfig() {
        // TODO: read config from xml file.
        return new ConnectionConfig("todo", "todo", "todo", "todo");
    }

    private void throwIfClosed() {
        if (!isOpen()) {
            throw new IllegalStateException("The factory is closed.");
        }
    }
}
