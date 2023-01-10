package org.kava.barattolo.entity.mappers;

import org.kava.barattolo.entity.database.DatabaseEntity;
import org.kava.barattolo.entity.database.DatabaseField;
import org.kava.barattolo.entity.database.DatabaseFieldDefinition;
import org.kava.barattolo.entity.object.ObjectEntity;
import org.kava.barattolo.entity.object.ObjectField;
import org.kava.barattolo.entity.object.ObjectFieldDefinition;

public class DatabaseObjectMapper {
    public static ObjectEntity map(DatabaseEntity databaseEntity, Class<?> objectClass) {
        return new ObjectEntity(
                objectClass,
                databaseEntity.fields().stream().map(DatabaseObjectMapper::map).toList()
        );
    }

    public static ObjectField map(DatabaseField databaseField) {
        ObjectFieldDefinition objectFieldDefinition = map(databaseField.fieldDefinition());
        Object value;
        if (databaseField.fieldDefinition().isComplexField()) {
            value = getProxy(objectFieldDefinition, databaseField.value());
        } else {
            value = databaseField.value();
        }
        return objectFieldDefinition.withValue(value);
    }

    public static ObjectFieldDefinition map(DatabaseFieldDefinition databaseFieldDefinition) {
        return ObjectFieldDefinition.of(databaseFieldDefinition.field());
    }

    private static Object getProxy(ObjectFieldDefinition fieldDefinition, Object databaseValue) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
