package org.kava.barattolo.manager;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import org.kava.barattolo.config.ConnectionConfig;
import org.kava.barattolo.config.ManagedClassesConfig;
import org.kava.barattolo.entity.database.DatabaseEntity;
import org.kava.barattolo.entity.database.DatabaseEntityDefinition;
import org.kava.barattolo.entity.database.DatabaseField;
import org.kava.barattolo.entity.database.DatabaseFieldDefinition;
import org.kava.barattolo.entity.mappers.DatabaseObjectMapper;
import org.kava.barattolo.query.DeleteQueryBuilder;
import org.kava.barattolo.query.InsertQueryBuilder;
import org.kava.barattolo.query.SelectQueryBuilder;
import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KavaEntityManager implements EntityManager {
    private final ConnectionConfig connectionConfig;
    private final ManagedClassesConfig managedClassesConfig;
    private EntityTransaction currentTransaction = new KavaEntityTransaction();
    private boolean isOpen = true;
    private final Logger logger = LoggerFactory.getLogger(KavaEntityManager.class, Level.DEBUG);

    public KavaEntityManager(ConnectionConfig connectionConfig, ManagedClassesConfig managedClassesConfig) {
        this.connectionConfig = connectionConfig;
        this.managedClassesConfig = managedClassesConfig;
    }

    @Override
    public void persist(Object entity) {
        DatabaseEntity databaseEntity = DatabaseEntity.of(entity);

        try (Connection connection = createConnection();
             PreparedStatement statement = new InsertQueryBuilder()
                     .withTable(databaseEntity.tableName())
                     .withFields(databaseEntity.fields())
                     .build(connection)) {
            logQuery(statement.toString());
            statement.execute();
        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T merge(T entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Object entity) {
        DatabaseEntity databaseEntity = DatabaseEntity.of(entity);

        try (Connection connection = createConnection();
             PreparedStatement statement = new DeleteQueryBuilder()
                     .withTable(databaseEntity.tableName())
                     .withPrimaryKey(databaseEntity.getPrimaryKey())
                     .build(connection)) {
            statement.execute();
            logQuery(statement.toString());
        } catch (SQLException e) {
            logSqlException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return find(entityClass, primaryKey, Map.of());
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        DatabaseEntityDefinition databaseEntityDefinition = DatabaseEntityDefinition.of(entityClass);
        DatabaseField primaryKeyField = databaseEntityDefinition
                .getPrimaryKey()
                .withValue(primaryKey);
        List<DatabaseField> foundDatabaseFields = new ArrayList<>();

        try (Connection connection = createConnection();
             PreparedStatement statement = new SelectQueryBuilder()
                     .withTable(databaseEntityDefinition.tableName())
                     .withPrimaryKeyField(primaryKeyField)
                     .build(connection)) {
            logQuery(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                for (DatabaseFieldDefinition databaseFieldDefinition : databaseEntityDefinition.fields()) {
                    Object resultFieldDatabaseValue = resultSet.getObject(databaseFieldDefinition.name());
                    foundDatabaseFields.add(
                            databaseFieldDefinition.withValue(resultFieldDatabaseValue)
                    );
                }
                DatabaseEntity databaseEntity = new DatabaseEntity(
                        databaseEntityDefinition.tableName(),
                        foundDatabaseFields
                );

               return (T) DatabaseObjectMapper.map(databaseEntity, entityClass).toObject();
            } else {
                return null;
            }
        } catch (SQLException e) {
            logSqlException(e);
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
        // TODO: throw in other methods if closed.
        isOpen = false;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public EntityTransaction getTransaction() {
        return currentTransaction;
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

    private Connection createConnection() throws SQLException {
        logger.debug("Creating a new connection to database with url: %s", connectionConfig.url());
        return DriverManager.getConnection(
                connectionConfig.url(), connectionConfig.username(), connectionConfig.password()
        );
    }

    private void logQuery(String query) {
        logger.debug("Executing query: %s", query);
    }

    private void logSqlException(SQLException exception) {
        logger.error("Encountered an SqlException: %s", exception);
    }
}
