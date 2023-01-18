package org.kava.barattolo.entity.database;

public record DatabaseField(
        DatabaseFieldDefinition fieldDefinition,
        Object value
) {
    public boolean isPrimaryKey() {
        return fieldDefinition.isPrimaryKey();
    }
}
