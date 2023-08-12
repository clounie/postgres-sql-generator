package com.clounie.sql;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @param schemaName optional schema this reference points to
 * @param tableName database table this reference points to
 */

public record TableRef(@Nullable String schemaName, @NonNull String tableName) implements ColumnFamily, SqlSerializable {

  public TableRef {
    if (StringUtils.isEmpty(tableName)) {
      throw new IllegalArgumentException(String.format("tableName cannot be empty but is: %1$s", tableName));
    }
  }

  @Override
  public String toSql() {
    if (StringUtils.isEmpty(schemaName)) {
      return "\"" + tableName + "\"";
    } else {
      return "\"" + schemaName + "\".\"" + tableName + "\"";
    }
  }
}
