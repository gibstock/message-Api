package com.gibstock.Util;

import java.io.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
  private static String url = getUrl();

  private static String user = getUser();

  private static String password = getPassword();

  private static Connection connection = null;

  public static String getUrl() {
    try {
      Properties p = new Properties();
      p.load(getReader());
      return p.getProperty("DB_CONNECTION_STRING");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getUser() {
    try {
      Properties p = new Properties();
      p.load(getReader());
      return p.getProperty("DB_USER");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getPassword() {
    try {
      Properties p = new Properties();
      p.load(getReader());
      return p.getProperty("DB_CONNECTION_PASSWORD");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static FileReader getReader() throws Exception {
    FileReader reader = new FileReader(
        "C:/projects/java/messageapi/messageApi/src/resources/messageApi.properties");
    return reader;
  }

  public static Connection getConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

}
