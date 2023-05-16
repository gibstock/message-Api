package com.gibstock.DAO;

import com.gibstock.Model.Account;

public interface AccountDAO {
  Account registerAccount(Account account);

  Account accountLogin(String username, String password);
}
