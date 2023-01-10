package org.kava.barattolo.query;

import org.kava.barattolo.entity.database.DatabaseField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SelectQueryBuilder {
    private String tableName = null;
    private DatabaseField primaryKeyField = null;
    private final List<String> selectFields = List.of("*");

    public SelectQueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SelectQueryBuilder withPrimaryKeyField(DatabaseField field) {
        this.primaryKeyField = field;
        return this;
    }

    public PreparedStatement build(Connection connection) {
        if (tableName == null) {
            throw new IllegalStateException("Table name must be specified!");
        } else if (primaryKeyField == null) {
            throw new IllegalStateException("Primary key must be specified!");
        }

        String query = new GenericQueryBuilder()
                .withSelect(selectFields, tableName)
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
