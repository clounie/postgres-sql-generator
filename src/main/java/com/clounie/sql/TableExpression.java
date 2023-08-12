package com.clounie.sql;

import java.util.Objects;

/**
 * @param tableRef definition of the column family this table expression refers to
 * @param aliasName optional alias for tableRef
 */

public record TableExpression(TableRef tableRef, String aliasName) implements ColumnFamily, SqlAliasable, SqlSerializable {
  @Override
  public String toSql() {
    return Objects.requireNonNullElseGet(aliasName, tableRef::toSql);
  }

  @Override
  public String getSqlForAliasDefinition() {
    return String.format("%1$s AS %2$s", tableRef.toSql(), aliasName);
  }
}
