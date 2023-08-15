package com.clounie.sql;

import com.clounie.sql.select.term.ColumnExpression;
import com.clounie.sql.select.SelectExpression;
import com.clounie.sql.select.term.ColumnRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Read-only SQL query builder
 */
public class SqlQuery implements SqlSerializable {

  /**
   * Every field used in the select of this SQL statement
   */
  private final List<SelectExpression> selectExpressions = new ArrayList<>();

  private TableExpression primaryTableExpression;

  /**
   * @param primaryTableExpression the primary table and schema for this SQL query. If a table is not
   *                               passed for a {@link ColumnExpression}, the primary table + schema
   *                               will be used.
   * @return
   */
  private SqlQuery(TableExpression primaryTableExpression) {
    this.primaryTableExpression = primaryTableExpression;
  }

  public static SqlQuery from(String tableName) {
    return new SqlQuery(new TableExpression(new TableRef(null, tableName), null));
  }
  public static SqlQuery from(String schemaName, String tableName) {
    return new SqlQuery(new TableExpression(new TableRef(schemaName, tableName), null));
  }

  public static SqlQuery from(String schemaName, String tableName, String aliasName) {
    return new SqlQuery(new TableExpression(new TableRef(schemaName, tableName), aliasName));
  }

  public SqlQuery select(SelectExpression... selectExpressions) {
    this.selectExpressions.addAll(List.of(selectExpressions));
    return this;
  }

  /**
   * Utility for adding {@code n} fields to the select. ASSUMPTION: all of these fields belong
   * to the primary table.<br><br>
   *
   * Supports jsonb {@code ->>} syntax. For example, one of the fields passed could be {@code myField->>myJsonProperty}
   *
   * @param fields fields to add to select
   * @return this query
   */
  public SqlQuery select(String... fields) {
    // TODO how does this work with JSON - think more
    this.selectExpressions.addAll(Arrays.stream(fields).map(
      field -> SelectExpression.getExpression(primaryTableExpression, field, null)
    ).toList());
    return this;
  }

  @Override
  public String toSql() {
    final StringBuilder sql = new StringBuilder("SELECT");
    for (SelectExpression expression : selectExpressions) {
      sql.append(String.format("\n\t%1$s", expression.getSqlForAliasDefinition()));
    }
    sql.append("\nFROM ").append(primaryTableExpression.getSqlForAliasDefinition());
    return sql.toString();
  }

  public static void main(String[] args) {
    final TableRef primaryTable = new TableRef("schema", "table");
    final TableExpression table = new TableExpression(primaryTable, null);
    final SelectExpression selectExpression = new SelectExpression(
      new ColumnExpression(null, new ColumnRef(primaryTable, "somecolumn", null)),
      null
    );
    SqlQuery query = new SqlQuery(table).select(selectExpression);
    System.out.println(query.toSql());
  }
}
