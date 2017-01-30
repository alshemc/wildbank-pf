/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.repository;

import com.wildwestbank.wildbank.jsf.model.AccountTransaction;
import com.wildwestbank.wildbank.jsf.model.Client;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alesha
 */
public interface AccountTransactionRepository {

	/**
	 * Returns a list of {@link AccountTransaction}s from database.
	 * 
	 * @return
	 */
	List<AccountTransaction> list();

        List<AccountTransaction> listByFilter(Date dateFrom, Date dateTo, Client client);
        
	/**
	 * Reads a {@link AccountTransaction}
	 * 
	 * @param id
	 * @return
	 */
	AccountTransaction read(BigDecimal id);

	/**
	 * Creates a new {@link AccountTransaction}
	 * 
	 * @param accountTransaction
	 */
	void create(AccountTransaction accountTransaction);

	/**
	 * Updates a {@link Account} and returns the updated one
	 * 
	 * @param account
	 * @return
	 */
	AccountTransaction update(AccountTransaction accountTransaction);

	/**
	 * Deletes a {@link AccountTransaction}
	 * 
	 * @param accountTransaction
	 */
	void delete(AccountTransaction accountTransaction);

	/**
	 * Deletes a {@link AccountTransaction}
	 * 
	 * @param accountTransaction
	 */
	void delete(BigDecimal id);
    
}
