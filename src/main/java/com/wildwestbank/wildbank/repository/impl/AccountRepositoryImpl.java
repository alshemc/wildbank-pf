/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.repository.impl;

import com.wildwestbank.wildbank.jsf.model.Account;
import com.wildwestbank.wildbank.repository.AccountRepository;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alesha
 */
@Named
public class AccountRepositoryImpl implements AccountRepository{

	@PersistenceContext
	protected EntityManager em;

	@Override
	public List<Account> list() {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + Account.class.getName() + " x ") //
                                .append("WHERE (INVIS IS NULL) OR (INVIS <> TRUE)")
                                .append("ORDER BY x.id ASC ");

		return em.createQuery(jpql.toString(), Account.class).getResultList();
	}

	@Override
	public List<Account> listByClient(Integer clientId) {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + Account.class.getName() + " x ") //
                                .append("WHERE ((INVIS IS NULL) OR (INVIS <> TRUE))AND(x.clientId=" + clientId.toString() + ")")
                                .append("ORDER BY x.id ASC ");

		return em.createQuery(jpql.toString(), Account.class).getResultList();
	}
        
	@Override
	public Account read(Long id) {
		return em.find(Account.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void create(Account account) {
		em.persist(account);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Account update(Account account) {
		return em.merge(account);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(Account account) {
            //account.setInvis(Boolean.TRUE);
            //this.update(account);
		em.remove(account);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(Long id) {
		Account account = em.getReference(Account.class, id);
                //account.setInvis(Boolean.TRUE);
                //this.update(account);
		delete(account);
	}    
}
