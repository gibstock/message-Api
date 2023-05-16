package com.gibstock.Service;

import com.gibstock.Model.Account;

public interface AccountService {
  Account addNewAccount(Account account);

  Account accountLogin(String username, String password);
}
