package com.clounie.sql.select.term;

import com.clounie.sql.ColumnFamily;
import com.clounie.sql.SqlSerializable;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * @param columnFamily optional column family name for this column
 * @param columnName name of the column
 * @param jsonPath list of json references, from top-level to bottom level,
 *                 to add to the query. e.g. ("Field1", "Field2") would produce
 *                 mycol->>'Field1'-->'Field2'
 */
public record ColumnRef(
  @Nullable ColumnFamily columnFamily,
  @NonNull String columnName,
  @Nullable List<String> jsonPath
) implements SqlSerializable {

  public ColumnRef {
    if (StringUtils.isEmpty(columnName)) {
      throw new IllegalArgumentException("columnName cannot be empty.");
    }
  }

  @Override
  public String toSql() {
    final StringBuilder sql = new StringBuilder();
    if (columnFamily != null) {
      sql.append(columnFamily.toSql()).append(".");
    }

    sql.append("\"").append(columnName).append("\"");

    if (jsonPath != null) {
      jsonPath.forEach(path -> {
        if (StringUtils.isNotEmpty(path)) {
          sql.append("->>'").append(path).append("'");
        }
      });
    }
    return sql.toString();
  }
}
