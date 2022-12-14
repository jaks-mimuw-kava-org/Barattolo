package org.kava.barattolo.query;

import org.kava.barattolo.entity.EntityField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteQueryBuilder {
    private String tableName = null;
    private final List<EntityField> primaryKeyFields = new ArrayList<>();

    public DeleteQueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder withPrimaryKeyFields(List<EntityField> fields) {
        primaryKeyFields.addAll(fields);
        return this;
    }

    public PreparedStatement build(Connection connection) {
        if (tableName == null) {
            throw new IllegalStateException("Table name must be specified!");
        } else if (primaryKeyFields.isEmpty()) {
            throw new IllegalStateException("Primary key must be specified!");
        }

        GenericQueryBuilder queryBuilder = new GenericQueryBuilder().withDelete(tableName);
        for (EntityField pk : primaryKeyFields) {
            queryBuilder = queryBuilder.withWhere(pk.fieldDefinition().tableFieldName(), "?");
        }
        String query = queryBuilder.build();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < primaryKeyFields.size(); i++) {
                statement.setObject(i + 1, primaryKeyFields.get(i).value());
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
