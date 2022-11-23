package query;

import com.kava.query.GenericQueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenericQueryBuilderTest {
    private static final String TEST_TABLE = "test_table";
    private static final String TEST_FIELD1 = "test_field1";
    private static final String TEST_VALUE1 = "test_value1";
    private static final String TEST_FIELD2 = "test_field2";
    private static final String TEST_VALUE2 = "test_value2";
    private static final List<String> TEST_FIELDS = List.of(TEST_FIELD1, TEST_FIELD2);
    private static final List<String> TEST_VALUES = List.of(TEST_VALUE1, TEST_VALUE2);

    @Test
    public void shouldCreateSelectQuery() {
        // when
        String query = new GenericQueryBuilder()
                .withSelect(TEST_FIELDS, TEST_TABLE)
                .build();

        // then
        Assertions.assertEquals(query, "SELECT test_field1,test_field2 FROM test_table");
    }

    @Test
    public void shouldCreateSelectQueryWithWhere() {
        // when
        String query = new GenericQueryBuilder()
                .withSelect(TEST_FIELDS, TEST_TABLE)
                .withWhere(TEST_FIELD1, TEST_VALUE1)
                .withWhere(TEST_FIELD2, TEST_VALUE2)
                .build();

        // then
        Assertions.assertEquals(query,
                "SELECT test_field1,test_field2 FROM test_table WHERE test_field1 = test_value1 AND test_field2 = test_value2");
    }

    @Test
    public void shouldCreateInsertQuery() {
        // when
        String query = new GenericQueryBuilder()
                .withInsert(TEST_TABLE, TEST_FIELDS, TEST_VALUES)
                .build();

        // then
        Assertions.assertEquals(query,
                "INSERT INTO test_table(test_field1,test_field2) VALUES(test_value1,test_value2)");
    }

    @Test
    public void shouldCreateDeleteQuery() {
        // when
        String query = new GenericQueryBuilder()
                .withDelete(TEST_TABLE)
                .build();

        // then
        Assertions.assertEquals(query, "DELETE FROM test_table");
    }

    @Test
    public void shouldCreateDeleteQueryWithWhere() {
        // when
        String query = new GenericQueryBuilder()
                .withDelete(TEST_TABLE)
                .withWhere(TEST_FIELD1, TEST_VALUE1)
                .withWhere(TEST_FIELD2, TEST_VALUE2)
                .build();

        // then
        Assertions.assertEquals(query,
                "DELETE FROM test_table WHERE test_field1 = test_value1 AND test_field2 = test_value2");
    }
}
