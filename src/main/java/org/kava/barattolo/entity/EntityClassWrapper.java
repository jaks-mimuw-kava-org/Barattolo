package org.kava.barattolo.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassWrapper {
    private final List<EntityFieldDefinition> fieldDefinitions;
    private final String entityName;

    public EntityClassWrapper(Class<?> entityClass) {
        fieldDefinitions = generateEntityFieldDefinitions(entityClass);
        entityName = entityClass.getSimpleName();
    }

    public String getTableName() {
        return entityName;
    }

    public List<EntityFieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public List<EntityFieldDefinition> getPrimaryKeyFields() {
        return getFieldDefinitions().stream()
                .filter(EntityFieldDefinition::isPrimaryKey)
                .toList();
    }

    private static List<EntityFieldDefinition> generateEntityFieldDefinitions(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .map(EntityFieldDefinition::of)
                .collect(Collectors.toList());
    }
}
