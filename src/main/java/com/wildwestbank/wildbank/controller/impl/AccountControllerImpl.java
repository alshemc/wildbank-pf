/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.controller.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.controller.AccountController;
import com.wildwestbank.wildbank.jsf.model.Account;
import com.wildwestbank.wildbank.repository.AccountRepository;

/**
 *
 * @author Alesha
 */
@Named
public class AccountControllerImpl implements AccountController {

	@Inject
	private AccountRepository repository;

	@Override
	public List<Account> list() {
            return repository.list();
	}

        @Override
        public List<Account> listByClient(Integer clientId){
            return repository.listByClient(clientId);
        }
        
	@Override
	public Account read(Long id) {
		return repository.read(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Account account) {
		repository.create(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Account update(Account account) {
		return repository.update(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Account account) {
		repository.delete(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		repository.delete(id);
	}

    
}
