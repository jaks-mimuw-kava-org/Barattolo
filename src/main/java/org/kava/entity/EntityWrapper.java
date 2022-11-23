package org.kava.entity;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityWrapper {
    private final List<EntityField> fields;
    private final String entityName;

    public EntityWrapper(Object entity) {
        fields = generateEntityFields(entity);
        entityName = entity.getClass().getSimpleName();
    }

    public String getTableName() {
        return entityName;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public List<EntityField> getPrimaryKeyFields() {
        return getFields().stream()
                .filter(EntityField::isPrimaryKey)
                .toList();
    }

    public static <T> List<EntityField> getEntityClassPrimaryKeyFields(Class<T> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .map(field -> toEntityField(field, null))
                .filter(EntityField::isPrimaryKey)
                .collect(Collectors.toList());
    }

    private static List<EntityField> generateEntityFields(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .map(field -> toEntityField(field, entity))
                .collect(Collectors.toList());
    }

    private static EntityField toEntityField(Field field, Object entity) {
        field.setAccessible(true);

        try {
            return new EntityField(
                    field.getName(),
                    field.getDeclaringClass(),
                    entity == null ? null : field.get(entity),
                    field.isAnnotationPresent(Id.class)
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
