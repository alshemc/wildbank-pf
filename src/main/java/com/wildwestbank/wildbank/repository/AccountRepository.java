/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.repository;

import com.wildwestbank.wildbank.jsf.model.Account;
import java.util.List;

/**
 *
 * @author Alesha
 */
public interface AccountRepository {

	/**
	 * Returns a list of {@link Account}s from database.
	 * 
	 * @return
	 */
	List<Account> list();

        /**
	 * Returns a list of {@link Account}s from database.
	 * 
	 * @return
	 */
	List<Account> listByClient(Integer clientId);
        
	/**
	 * Reads a {@link Account}
	 * 
	 * @param id
	 * @return
	 */
	Account read(Long id);

	/**
	 * Creates a new {@link Account}
	 * 
	 * @param account
	 */
	void create(Account account);

	/**
	 * Updates a {@link Account} and returns the updated one
	 * 
	 * @param account
	 * @return
	 */
	Account update(Account account);

	/**
	 * Deletes a {@link Account}
	 * 
	 * @param account
	 */
	void delete(Account account);

	/**
	 * Deletes a {@link Account}
	 * 
	 * @param account
	 */
	void delete(Long id);
    
}
