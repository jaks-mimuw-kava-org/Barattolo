package org.kava.barattolo.query;

import org.kava.barattolo.entity.database.DatabaseField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryBuilder {
    private String tableName = null;
    private DatabaseField primaryKeyField = null;

    public DeleteQueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder withPrimaryKey(DatabaseField primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
        return this;
    }

    public PreparedStatement build(Connection connection) {
        if (tableName == null) {
            throw new IllegalStateException("Table name must be specified!");
        } else if (primaryKeyField == null) {
            throw new IllegalStateException("Primary key must be specified!");
        }

        String query = new GenericQueryBuilder()
                .withDelete(tableName)
                .withWhere(primaryKeyField.fieldDefinition().name(), "?")
                .build();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, primaryKeyField.value());

            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
