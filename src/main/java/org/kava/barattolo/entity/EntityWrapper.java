package org.kava.barattolo.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityWrapper {
    private final List<EntityField> fields;
    private final Class<?> entityClass;

    public EntityWrapper(Object entity) {
        fields = generateEntityFields(entity);
        entityClass = entity.getClass();
    }

    public EntityWrapper(Class<?> entityClass, List<EntityField> fields) {
        this.fields = fields;
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return entityClass.getSimpleName();
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public List<EntityField> getPrimaryKeyFields() {
        return getFields().stream()
                .filter(field -> field.fieldDefinition().isPrimaryKey())
                .toList();
    }

    public Object toObject() {
        try {
            Object mappedObject = entityClass.getConstructor().newInstance();
            for (EntityField field : fields) {
                field.fieldDefinition().field().setAccessible(true);
                field.fieldDefinition().field().set(mappedObject, field.value());
            }

            return mappedObject;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<EntityField> generateEntityFields(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .map(field -> toEntityField(field, entity))
                .collect(Collectors.toList());
    }

    private static EntityField toEntityField(Field field, Object entity) {
        field.setAccessible(true);

        try {
            EntityFieldDefinition fieldDefinition = EntityFieldDefinition.of(field);
            return fieldDefinition.withValue(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
