package com.clounie.sql.select.term;

import com.clounie.sql.SqlQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class IntegrationTests {

  @Test
  void testBasicQuery() {
    final String myTable = "myTable";
    final SqlQuery query = SqlQuery.from(null, myTable, null);

    final String result = query.select("MyField").toSql();
    Assertions.assertEquals("SELECT\n\t\"myTable\".\"MyField\"\nFROM \"" + myTable + "\"", result);
  }

  @Test
  void testFieldAliasing() {
    final String myTable = "myTable";
    final SqlQuery query = SqlQuery.from(null, myTable, null);

    final String result = query.select("MyField").toSql();
    Assertions.assertEquals("SELECT\n\t\"myTable\".\"MyField\"\nFROM \"" + myTable + "\"", result);
  }

  private void queryAndVerifyData(String sqlQuery, List<Map<String, Object>> data) {
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT * FROM mytable WHERE columnfoo = 500");
    while (rs.next()) {
      System.out.print("Column 1 returned ");
      System.out.println(rs.getString(1));
    }
    rs.close();
    st.close();
  }
}
