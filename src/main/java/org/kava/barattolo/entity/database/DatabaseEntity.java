package org.kava.barattolo.entity.database;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record DatabaseEntity(String tableName,
                             Class<?> objectClass,
                             List<DatabaseField> fields) {
    public DatabaseField getPrimaryKey() {
        return fields.stream()
                .filter(DatabaseField::isPrimaryKey)
                .findFirst()
                .get();
    }

    public static DatabaseEntity of(Object entity) {
        return new DatabaseEntity(
                retrieveTableName(entity),
                entity.getClass(),
                generateDatabaseFields(entity)
        );
    }

    private static String retrieveTableName(Object entity) {
        return entity.getClass().getSimpleName();
    }

    private static List<DatabaseField> generateDatabaseFields(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .map(it -> generateDatabaseField(it, entity))
                .toList();
    }

    private static DatabaseField generateDatabaseField(Field field, Object entity) {
        DatabaseFieldDefinition databaseFieldDefinition = DatabaseFieldDefinition.of(field);
        Object databaseFieldValue = generateDatabaseFieldValue(field, entity, databaseFieldDefinition);

        return databaseFieldDefinition.withValue(databaseFieldValue);
    }

    private static Object generateDatabaseFieldValue(Field field,
                                                     Object entity,
                                                     DatabaseFieldDefinition databaseFieldDefinition) {
        Object fieldValue;
        try {
            fieldValue = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (databaseFieldDefinition.isComplexField()) {
            return retrievePrimaryKey(fieldValue);
        } else {
            return fieldValue;
        }
    }

    private static Object retrievePrimaryKey(Object complexField) {
        DatabaseEntity databaseEntity = DatabaseEntity.of(complexField);
        return databaseEntity.getPrimaryKey().value();
    }
}
