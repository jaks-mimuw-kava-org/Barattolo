package com.kava.query;

import com.kava.entity.EntityField;

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

        String initialQuery = "DELETE FROM %s WHERE %s = ?".formatted(tableName, primaryKeyFields.get(0).name());
        StringBuilder finalQueryBuilder = new StringBuilder(initialQuery);
        for (int i = 1; i < primaryKeyFields.size(); i++) {
            finalQueryBuilder.append(" AND %s = ? ".formatted(primaryKeyFields.get(i).name()));
        }
        String finalQuery = finalQueryBuilder.toString();

        try {
            PreparedStatement statement = connection.prepareStatement(finalQuery);
            for (int i = 0; i < primaryKeyFields.size(); i++) {
                statement.setObject(i + 1, primaryKeyFields.get(i).value());
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
