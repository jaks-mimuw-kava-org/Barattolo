package org.kava.barattolo.manager;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.Query;
import jakarta.persistence.SynchronizationType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;
import org.kava.barattolo.config.ConnectionConfig;
import org.kava.barattolo.config.DriverConfig;
import org.kava.barattolo.config.ManagedClassesConfig;

import java.util.Map;

public class KavaEntityManagerFactory implements EntityManagerFactory {
    private final DriverConfig driverConfig;
    private final ConnectionConfig connectionConfig;
    private final ManagedClassesConfig managedClassesConfig;
    private boolean isOpen = true;

    public KavaEntityManagerFactory(DriverConfig driverConfig, ConnectionConfig connectionConfig,
                                    ManagedClassesConfig managedClassesConfig) {
        this.driverConfig = driverConfig;
        this.connectionConfig = connectionConfig;
        this.managedClassesConfig = managedClassesConfig;
    }

    @Override
    public EntityManager createEntityManager() {
        return createEntityManager(Map.of());
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        throwIfClosed();

        return new KavaEntityManager(connectionConfig, managedClassesConfig);
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

    private void throwIfClosed() {
        if (!isOpen()) {
            throw new IllegalStateException("The factory is closed.");
        }
    }
}
