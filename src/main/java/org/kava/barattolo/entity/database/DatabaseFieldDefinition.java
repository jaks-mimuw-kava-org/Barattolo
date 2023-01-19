package org.kava.barattolo.entity.database;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.lang.reflect.Field;

public record DatabaseFieldDefinition(
        Field field,
        String name,
        DatabaseFieldType fieldType
) {

    public DatabaseField withValue(Object value) {
        return new DatabaseField(this, value);
    }

    public DatabaseField getValue(Object entity) {
        try {
            return this.withValue(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isComplexField() {
        return fieldType != DatabaseFieldType.FIELD && fieldType != DatabaseFieldType.PRIMARY_KEY;
    }

    public boolean isPrimaryKey() {
        return this.fieldType == DatabaseFieldType.PRIMARY_KEY;
    }

    public static DatabaseFieldDefinition of(Field field) {
        field.setAccessible(true);

        return new DatabaseFieldDefinition(field, retrieveFieldName(field), retrieveFieldType(field));
    }

    private static String retrieveFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name();
        } else {
            return field.getName();
        }
    }

    private static DatabaseFieldType retrieveFieldType(Field field) {
        if (isPrimaryKey(field)) {
            return DatabaseFieldType.PRIMARY_KEY;
        } else if (isForeignKey(field)) {
            return DatabaseFieldType.FOREIGN_KEY;
        } else {
            return DatabaseFieldType.FIELD;
        }
    }

    private static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private static boolean isForeignKey(Field field) {
        return field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class);
    }

    // TODO: what to do when there is many to many or one to many? In database there will be another table for it.
}
