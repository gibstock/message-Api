package com.gibstock.Service;

import com.gibstock.DAO.AccountDAO;
import com.gibstock.DAO.AccountDAOImpl;
import com.gibstock.Model.Account;

public class AccountServiceImpl implements AccountService {

  private AccountDAO accountDao;

  public AccountServiceImpl() {
    accountDao = new AccountDAOImpl();
  }

  @Override
  public Account addNewAccount(Account account) {
    return accountDao.registerAccount(account);
  }

  @Override
  public Account accountLogin(String username, String password) {
    return accountDao.accountLogin(username, password);
  }

}
