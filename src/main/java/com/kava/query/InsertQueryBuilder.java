package com.kava.query;

import com.kava.entity.EntityField;
import com.kava.entity.EntityWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class InsertQueryBuilder {
    private String tableName = null;
    private EntityWrapper entityWrapper = null;

    public InsertQueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertQueryBuilder withEntity(EntityWrapper entityWrapper) {
        this.entityWrapper = entityWrapper;
        return this;
    }

    public PreparedStatement build(Connection connection) {
        if (tableName == null) {
            throw new IllegalStateException("Table name must be specified!");
        } else if (entityWrapper == null) {
            throw new IllegalStateException("Inserting entity must be specified!");
        }

        List<EntityField> fields = entityWrapper.getFields();
        List<String> fieldNames = fields.stream().map(EntityField::name).toList();
        String initialQuery = "INSERT INTO %s(%s) VALUES(%s)".formatted(
                tableName,
                String.join(",", fieldNames),
                String.join(",", Collections.nCopies(fields.size(), "?"))
        );
        System.out.println(initialQuery);

        try {
            PreparedStatement statement = connection.prepareStatement(initialQuery);
            for (int i = 0; i < fields.size(); i++) {
                statement.setObject(i + 1, fields.get(i).value());
                System.out.println(statement);
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
