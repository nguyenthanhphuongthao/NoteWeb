package com.calendar.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.Entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long>{
	Account findByUsernameAndPassword(String username, String password);

	@Query("SELECT a FROM Account a WHERE username=?1")
	public Account findByUsername(String username);
}
