/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.controller;

import java.math.BigDecimal;
import java.util.List;
import com.wildwestbank.wildbank.jsf.model.AccountTransaction;
import com.wildwestbank.wildbank.jsf.model.Client;
import java.util.Date;

/**
 *
 * @author Alesha
 */
public interface AccountTransactionController {

	/**
	 * @return mock list of codes
	 */
	List<AccountTransaction> list();

        /**
	 * @return mock list of codes
	 */
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
	 * Updates a {@link AccountTransaction} and returns the updated one
	 * 
	 * @param accountTransaction
	 * @return
	 */
	AccountTransaction update(AccountTransaction accountTransaction);

	/**
	 * Deletes a {@link AccountTransaction}.
	 * 
	 * @param accountTransaction
	 */
	void delete(AccountTransaction accountTransaction);

	/**
	 * Deletes a {@link AccountTransaction}.
	 * 
	 * @param id
	 */
	void delete(BigDecimal id);

}
