package com.clounie.sql.calculations;

import com.clounie.sql.SqlSerializable;
import com.clounie.sql.select.term.ColumnRef;

import java.util.ArrayList;
import java.util.List;


public class AdditionCalc implements SqlSerializable {

  private final List<ColumnRef> dependentFields = new ArrayList<>();
  private List<Object>          objectsToAdd    = new ArrayList<>();

  public AdditionCalc() {

  }

  public List<ColumnRef> getDependentFields() {
    return dependentFields;
  }

  public AdditionCalc add(Integer num) {
    objectsToAdd.add(num);
    return this;
  }

  public AdditionCalc add(ColumnRef target) {
    dependentFields.add(target);
    objectsToAdd.add(target);
    return this;
  }

  @Override
  public String toSql() {
    final StringBuilder builder = new StringBuilder();
    if (objectsToAdd.isEmpty()) {
       return "";
    }

    for(int i = 0; i < objectsToAdd.size(); i++) {
      builder.append(objectToString(objectsToAdd.get(i)));
      if (i < objectsToAdd.size() - 1) {
        builder.append(" + ");
      }
    }
    return builder.toString();
  }

  private String objectToString(Object obj) {
    if (obj instanceof Integer num) {
      return num.toString();
    } else if (obj instanceof ColumnRef target) {
      return target.toSql();
    } else {
      throw new IllegalArgumentException("obj is not of known type - " + obj);
    }
  }
}
