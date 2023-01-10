package org.kava.barattolo.entity.database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record DatabaseEntityDefinition(
        String tableName,
        List<DatabaseFieldDefinition> fields
) {
    public DatabaseFieldDefinition getPrimaryKey() {
        return fields.stream()
                .filter(DatabaseFieldDefinition::isPrimaryKey)
                .findFirst()
                .get();
    }

    public static DatabaseEntityDefinition of(Class<?> entityClass) {
        return new DatabaseEntityDefinition(
                entityClass.getSimpleName(),
                generateFieldDefinitions(entityClass)
        );
    }

    private static List<DatabaseFieldDefinition> generateFieldDefinitions(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .map(DatabaseFieldDefinition::of)
                .collect(Collectors.toList());
    }
}
