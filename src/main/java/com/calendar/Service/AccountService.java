package com.calendar.Service;

import org.springframework.stereotype.Service;

import com.calendar.Entity.Account;

@Service
public interface AccountService {

	Account findByUsernameAndPassword(String username, String password);
	Account findByUsername(String username);
	void saveAccount(Account account);
}
