package com.clounie.sql.select.term;

import com.clounie.sql.select.SelectTerm;

/**
 * @param tableAlias name of the table alias this expression has, if any
 * @param columnRef the column being referenced by this column expression
 */
public record ColumnExpression(String tableAlias, ColumnRef columnRef) implements SelectTerm {
  @Override
  public String toSql() {
    if (tableAlias != null && !tableAlias.equals("")) {
      return String.format("%1$s.%2$s", tableAlias, columnRef.columnName());
    } else {
      return columnRef.toSql();
    }
  }
}
