package org.kava.barattolo.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kava.barattolo.entity.EntityField;
import org.kava.barattolo.entity.EntityWrapper;
import org.kava.barattolo.query.DeleteQueryBuilder;
import org.kava.barattolo.query.InsertQueryBuilder;
import org.kava.barattolo.query.SelectQueryBuilder;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KavaEntityManager implements EntityManager {
    private final ConnectionConfig connectionConfig;

    public KavaEntityManager(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    @Override
    public void persist(Object entity) {
        EntityWrapper entityWrapper = new EntityWrapper(entity);

        try {
            Connection connection = getConnection();
            PreparedStatement statement = new InsertQueryBuilder()
                    .withTable(entityWrapper.getTableName())
                    .withEntity(entityWrapper)
                    .build(connection);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T merge(T entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Object entity) {
        EntityWrapper entityWrapper = new EntityWrapper(entity);

        try {
            Connection connection = getConnection();
            PreparedStatement statement = new DeleteQueryBuilder()
                    .withTable(entityWrapper.getTableName())
                    .withPrimaryKeyFields(entityWrapper.getPrimaryKeyFields())
                    .build(connection);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return find(entityClass, primaryKey, Map.of());
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        String tableName = entityClass.getSimpleName();
        String primaryKeyFieldName = EntityWrapper.getEntityClassPrimaryKeyFields(entityClass).get(0).name();
        EntityField primaryKeyField = new EntityField(primaryKeyFieldName, Object.class, primaryKey, true);

        Map<String, Object> fieldsToValue = new HashMap<>();

        try {
            Connection connection = getConnection();
            PreparedStatement statement = new SelectQueryBuilder()
                    .withTable(tableName)
                    .withPrimaryKeyField(primaryKeyField)
                    .build(connection);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                for (Field field : entityClass.getDeclaredFields()) {
                    fieldsToValue.put(field.getName(), resultSet.getObject(field.getName()));
                }

                return new ObjectMapper().convertValue(fieldsToValue, entityClass);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FlushModeType getFlushMode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(Object entity, Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void detach(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LockModeType getLockMode(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createQuery(String qlString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createQuery(CriteriaUpdate updateQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createQuery(CriteriaDelete deleteQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNamedQuery(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNativeQuery(String sqlString, Class resultClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinTransaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isJoinedToTransaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDelegate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityTransaction getTransaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
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
    public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityGraph<?> createEntityGraph(String graphName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityGraph<?> getEntityGraph(String graphName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
        throw new UnsupportedOperationException();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                connectionConfig.url(), connectionConfig.username(), connectionConfig.password()
        );
    }
}