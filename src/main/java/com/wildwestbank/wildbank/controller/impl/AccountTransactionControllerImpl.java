/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.controller.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.controller.AccountTransactionController;
import com.wildwestbank.wildbank.jsf.model.AccountTransaction;
import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.repository.AccountTransactionRepository;
import java.util.Date;
/**
 *
 * @author Alesha
 */
@Named
public class AccountTransactionControllerImpl implements AccountTransactionController {
	@Inject
	private AccountTransactionRepository repository;

	@Override
	public List<AccountTransaction> list() {
		return repository.list();
	}

	@Override
	public List<AccountTransaction> listByFilter(Date dateFrom, Date dateTo, Client client) {
		return repository.listByFilter(dateFrom, dateTo, client);
	}
        
	@Override
	public AccountTransaction read(BigDecimal id) {
		return repository.read(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(AccountTransaction accountTransaction) {
		repository.create(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AccountTransaction update(AccountTransaction accountTransaction) {
		return repository.update(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AccountTransaction accountTransaction) {
		repository.delete(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(BigDecimal id) {
		repository.delete(id);
	}
    
}
