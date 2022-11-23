package com.kava.query;

import java.util.List;

public class GenericQueryBuilder {
    StringBuilder queryBuilder = new StringBuilder();

    private boolean whereClausePresent = false;

    public GenericQueryBuilder withSelect(List<String> selectFields, String from) {
        String selectFieldsStr = String.join(",", selectFields);
        queryBuilder.append("SELECT %s FROM %s".formatted(selectFieldsStr, from));
        return this;
    }

    public GenericQueryBuilder withDelete(String from) {
        queryBuilder.append("DELETE FROM %s".formatted(from));
        return this;
    }

    public GenericQueryBuilder withInsert(String into, List<String> insertFields, List<String> insertValues) {
        String insertFieldsStr = String.join(",", insertFields);
        String insertValuesStr = String.join(",", insertValues);
        queryBuilder.append("INSERT INTO %s(%s) VALUES(%s)".formatted(into, insertFieldsStr, insertValuesStr));

        return this;
    }

    public GenericQueryBuilder withWhere(String left, String right) {
        String joinKeyWord;
        if (whereClausePresent) {
            joinKeyWord = "AND";
        } else {
            joinKeyWord = "WHERE";
            whereClausePresent = true;
        }
        queryBuilder.append(" %s %s = %s".formatted(joinKeyWord, left, right));

        return this;
    }

    public String build() {
        return queryBuilder.toString();
    }
}
