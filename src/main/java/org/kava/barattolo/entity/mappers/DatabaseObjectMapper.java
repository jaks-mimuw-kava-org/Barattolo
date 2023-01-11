package org.kava.barattolo.entity.mappers;

import jakarta.persistence.EntityManager;
import org.kava.barattolo.entity.database.DatabaseEntity;
import org.kava.barattolo.entity.database.DatabaseField;
import org.kava.barattolo.entity.database.DatabaseFieldDefinition;
import org.kava.barattolo.entity.object.ObjectEntity;
import org.kava.barattolo.entity.object.ObjectField;
import org.kava.barattolo.entity.object.ObjectFieldDefinition;
import org.kava.barattolo.entity.proxy.LazyLoadingProxy;

public class DatabaseObjectMapper {
    private final EntityManager entityManager; // TODO: it probably shouldn't know about entity manager.
    public DatabaseObjectMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ObjectEntity map(DatabaseEntity databaseEntity, Class<?> objectClass) {
        return new ObjectEntity(
                objectClass,
                databaseEntity.fields().stream().map(this::map).toList()
        );
    }

    public ObjectField map(DatabaseField databaseField) {
        ObjectFieldDefinition objectFieldDefinition = map(databaseField.fieldDefinition());
        Object value;
        if (databaseField.fieldDefinition().isComplexField()) {
            value = getProxy(objectFieldDefinition, databaseField.value());
        } else {
            value = databaseField.value();
        }
        return objectFieldDefinition.withValue(value);
    }

    public ObjectFieldDefinition map(DatabaseFieldDefinition databaseFieldDefinition) {
        return ObjectFieldDefinition.of(databaseFieldDefinition.field());
    }

    private Object getProxy(ObjectFieldDefinition fieldDefinition, Object databaseValue) {
       return new LazyLoadingProxy().getProxy(fieldDefinition, databaseValue, entityManager);
    }
}
