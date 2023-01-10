package org.kava.barattolo.query;

import org.kava.barattolo.entity.database.DatabaseField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertQueryBuilder {
    private String tableName = null;
    private List<DatabaseField> fields = new ArrayList<>();

    public InsertQueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertQueryBuilder withFields(List<DatabaseField> fields) {
        this.fields.addAll(fields);
        return this;
    }

    public PreparedStatement build(Connection connection) {
        if (tableName == null) {
            throw new IllegalStateException("Table name must be specified!");
        } else if (fields.isEmpty()) {
            throw new IllegalStateException("Fields must be specified!");
        }

        List<String> fieldNames = fields.stream().map(field -> field.fieldDefinition().name()).toList();
        String query = new GenericQueryBuilder()
                .withInsert(tableName, fieldNames, Collections.nCopies(fields.size(), "?"))
                .build();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < fields.size(); i++) {
                statement.setObject(i + 1, fields.get(i).value());
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
