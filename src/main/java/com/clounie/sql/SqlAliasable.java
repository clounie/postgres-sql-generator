package com.clounie.sql;

/**
 * Many SQL elements can be represented by an expression, which has a SQL definition, and a reference.<br><br>
 *
 * For example, TableExpression is defined as "schema.table AS mytablealias"; however, it is referenced like "mytablealias"<br><br>
 *
 * This interface allows access to the <emphasis>definition</emphasis> of an expression, whereas {@link SqlSerializable#toSql()}
 * provides access to the reference.
 */

public interface SqlAliasable extends SqlSerializable {

  String getSqlForAliasDefinition();
}
