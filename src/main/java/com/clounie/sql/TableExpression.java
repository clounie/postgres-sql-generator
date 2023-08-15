package com.clounie.sql;

import org.apache.commons.lang3.StringUtils;

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
    if (StringUtils.isEmpty(aliasName)) {
      return toSql();
    } else {
      return String.format("%1$s AS %2$s", tableRef.toSql(), aliasName);
    }
  }

  public static TableExpression from(String schemaName, String tableName, String aliasName) {
    return new TableExpression(new TableRef(schemaName, tableName), aliasName);
  }
}
