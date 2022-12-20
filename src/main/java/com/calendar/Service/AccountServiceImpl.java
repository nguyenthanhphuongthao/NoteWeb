package com.calendar.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.DAO.AccountDAO;
import com.calendar.Entity.Account;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDAO accountDAO;

	@Override
	public Account findByUsernameAndPassword(String username, String password) {
		return accountDAO.findByUsernameAndPassword(username, password);
	}

	@Override
	public void saveAccount(Account account) {
		accountDAO.save(account);
	}

	@Override
	public Account findByUsername(String username) {
		return accountDAO.findByUsername(username);
	}
}
