package com.clounie.sql.select.term;

import com.clounie.sql.TableRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class ColumnRefTests {

  @Test
  void testBadNulls() {
    Assertions.assertThrows(IllegalArgumentException.class,
      () -> new ColumnRef(null, null, null),
      "Should not allow null columnName"
    );
    Assertions.assertThrows(IllegalArgumentException.class,
      () -> new ColumnRef(null, "", null),
      "Should not allow empty columnName"
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"columnName", "columnname", "ColumnName", "ColumnNamE"})
  void testOnlyRequiredValues(String columnName) {
    final String sql = new ColumnRef(null, columnName, null).toSql();
    Assertions.assertEquals(
      "\"" + columnName + "\"", sql,
      "Should have column name, keep case, and have quotes"
    );
  }

  @ParameterizedTest
  @MethodSource("provideArgsForColumnFamilyOnly")
  void testWithColumnFamilyOnly(String schema, String table, String colName, String expected) {
    final TableRef tableRef = new TableRef(schema, table);
    final String sql = new ColumnRef(tableRef, colName, null).toSql();
    Assertions.assertEquals(tableRef.toSql() + expected, sql, "Should eval to expected sql");
  }

  private static Stream<Arguments> provideArgsForColumnFamilyOnly() {
    return Stream.of(
      Arguments.of(null, "sometable", "somecol", ".\"somecol\""),
      Arguments.of("someschema", "sometable", "somecol", ".\"somecol\"")
    );
  }

  @ParameterizedTest
  @MethodSource("provideArgsForAllTest")
  void testAllArguments(String schema, String table, String colName, String expected) {
    final TableRef tableRef = new TableRef(schema, table);
    final String sql = new ColumnRef(tableRef, colName, null).toSql();
    Assertions.assertEquals(tableRef.toSql() + expected, sql, "Should eval to expected sql");
  }

  private static Stream<Arguments> provideArgsForAllTest() {
    return Stream.of(
      Arguments.of(null, "sometable", "somecol", ".\"somecol\""),
      Arguments.of("someschema", "sometable", "somecol", ".\"somecol\"")
    );
  }
}
