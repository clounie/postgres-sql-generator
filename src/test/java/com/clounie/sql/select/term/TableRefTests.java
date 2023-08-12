package com.clounie.sql.select.term;

import com.clounie.sql.TableRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class TableRefTests {

  @Test
  void testBadNulls() {
    Assertions.assertThrows(IllegalArgumentException.class,
      () -> new TableRef(null, null),
      "Should not allow null columnName"
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"tableName", "tablename", "TableName", "TableNamE"})
  void testOnlyRequiredValues(String tableName) {
    final String sql = new TableRef(null, tableName).toSql();
    Assertions.assertEquals(
      "\"" + tableName + "\"", sql,
      "Should have column name, keep case, and have quotes"
    );
  }

  @ParameterizedTest
  @MethodSource("provideArgsForAllTest")
  void testAllArguments(String schema, String table, String expected) {
    final String sql = new TableRef(schema, table).toSql();
    Assertions.assertEquals(expected, sql, "Should eval to expected sql");
  }

  private static Stream<Arguments> provideArgsForAllTest() {
    return Stream.of(
      Arguments.of("someSchema", "someTable", "\"someSchema\".\"someTable\""),
      Arguments.of("someschema", "sometable", "\"someschema\".\"sometable\""),
      Arguments.of("SomeSchema", "SomeTable", "\"SomeSchema\".\"SomeTable\""),
      Arguments.of("SomeSchemA", "SomeTablE", "\"SomeSchemA\".\"SomeTablE\"")
    );
  }
}
