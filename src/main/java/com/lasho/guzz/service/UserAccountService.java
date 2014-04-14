package com.lasho.guzz.service;

import java.util.List;

import com.lasho.guzz.domain.UserAccount;


public interface UserAccountService {

	/**
	 * save message
	 * @param message
	 */
	int save(UserAccount account);
	
	/**
	 * find by primary key
	 * @param id
	 * @return
	 */
	UserAccount findById(Integer id,Integer userId,Integer areaId) ;
	
	/**
	 * update
	 * @param message
	 * @return
	 */
	boolean update(UserAccount account);
	
	/**
	 * find all accounts
	 * @return
	 */
	List<UserAccount> findAllAccounts() ;
	
}
