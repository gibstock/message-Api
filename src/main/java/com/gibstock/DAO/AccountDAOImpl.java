package com.gibstock.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.gibstock.Model.Account;
import com.gibstock.Util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

  private Connection con;

  public AccountDAOImpl() {
    con = ConnectionUtil.getConnection();
  }

  @Override
  public Account registerAccount(Account account) {
    Account addedAccount = null;
    try {
      String sql = "INSERT INTO account (username, password) VALUES(?, ?)";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();

      // ResultSetMetaData meta = rs.getMetaData();
      // for (int index = 1; index <= meta.getColumnCount(); index++) {
      // System.out.println("Column " + index + " is named " +
      // meta.getColumnName(index));
      // }

      while (rs.next()) {
        int generatedKey = (int) rs.getLong(1);
        String username = account.getUsername();
        String password = account.getPassword();
        addedAccount = new Account(generatedKey, username, password);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return addedAccount;
  }

  @Override
  public Account accountLogin(String username, String password) {
    Account account = null;
    try {
      String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, username);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        int generatedKey = rs.getInt(1);
        account = new Account(generatedKey, username, password);
        System.out.println("Feel me?" + account);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

}
