package com.clounie.sql.select;

import com.clounie.sql.SqlAliasable;
import com.clounie.sql.TableExpression;
import com.clounie.sql.select.term.ColumnExpression;
import com.clounie.sql.select.term.ColumnRef;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public record SelectExpression(@NonNull SelectTerm term, @Nullable String columnAlias) implements SqlAliasable {

  public static SelectExpression getExpression(TableExpression tableExpression, @Nullable String fieldName, @Nullable String alias) {
    return new SelectExpression(
      new ColumnExpression(
        tableExpression.aliasName(),
        new ColumnRef(tableExpression.tableRef(), fieldName, null)
      ),
      alias
    );
  }

  @Override
  public String getSqlForAliasDefinition() {
    if (StringUtils.isNotEmpty(columnAlias)) {
      return String.format("%1$s AS %2$s", term.toSql(), columnAlias);
    } else {
      return term.toSql();
    }
  }

  @Override
  public String toSql() {
    if (StringUtils.isNotEmpty(columnAlias)) {
      return columnAlias;
    } else {
      return term.toSql();
    }
  }
}
