package com.example.demo.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQLUtils {

  public static boolean hasColumn(ResultSet resultSet, String columnName) throws SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int columns = resultSetMetaData.getColumnCount();
    for (int i = 1; i <= columns; i++) {
      if (columnName.equalsIgnoreCase(resultSetMetaData.getColumnName(i))) {
        return true;
      }
    }
    return false;
  }
}
