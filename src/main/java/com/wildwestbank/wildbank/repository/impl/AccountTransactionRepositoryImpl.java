/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.repository.impl;

import com.wildwestbank.wildbank.jsf.model.Account;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.jsf.model.AccountTransaction;
import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.repository.AccountTransactionRepository;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Alesha
 */
@Named
public class AccountTransactionRepositoryImpl implements AccountTransactionRepository{

	@PersistenceContext
	protected EntityManager em;

	@Override
	public List<AccountTransaction> list() {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + AccountTransaction.class.getName() + " x ") //
                                .append("WHERE (INVIS IS NULL) OR (INVIS <> TRUE)")
                                .append("ORDER BY x.id ASC ");

		return em.createQuery(jpql.toString(), AccountTransaction.class).getResultList();
	}
        
        @Override
        public List<AccountTransaction> listByFilter(Date dateFrom, Date dateTo, Client client){
            /*
            select t.*, ac.client_id as client_from, bc.client_id as client_to from ACCOUNT_TRANSACTION t
                LEFT JOIN account ac ON t.ACCOUNT_FROM = ac.account_id
                LEFT JOIN account bc ON t.ACCOUNT_TO = bc.account_id 
                WHERE (t.TR_DATE BETWEEN '2016-04-15' AND '2016-04-18')AND((ac.client_id = 1)OR(bc.client_id = 2)); */
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + AccountTransaction.class.getName() + " x ") //
                                .append("LEFT JOIN x.accountFrom ac ")  //Account.class.getName() + " ac ON x.ACCOUNT_FROM = ac.ACCOUNT_ID ")
                                .append("LEFT JOIN x.accountTo bc ") //Account.class.getName() + " bc ON x.ACCOUNT_TO = bc.ACCOUNT_ID ") 
                                .append("WHERE ((x.invis IS NULL) OR (x.invis <> TRUE)) ");
                Integer clientId = null;
                if (dateFrom!=null)
                    jpql.append("AND (x.trDate >= :startDate ) ");
                
                if (dateTo!=null)
                    jpql.append("AND (x.trDate <= :endDate ) ");
                
                if (client!=null){
                    jpql.append("AND((ac.clientId = :clientId)OR(bc.clientId = :clientId)) ");
                    //jpql.append("AND (ac.clientId = :clientId) ");
                    clientId = client.getClientId();
                }
                jpql.append("ORDER BY x.id ASC ");
                
                Query qr = em.createQuery(jpql.toString(), AccountTransaction.class);
                if (dateFrom!=null)
                    qr = qr.setParameter("startDate", dateFrom, TemporalType.DATE);
                if (dateTo!=null)
                    qr = qr.setParameter("endDate", dateTo, TemporalType.DATE);
                if (client!=null)
                    qr = qr.setParameter("clientId", client);
                return qr.getResultList();
        }

	@Override
	public AccountTransaction read(BigDecimal id) {
		return em.find(AccountTransaction.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void create(AccountTransaction accountTransaction) {
            Account fromAccount = accountTransaction.getAccountFrom();
            Account toAccount = accountTransaction.getAccountTo();
            Double trValue = accountTransaction.getTrValue();
            fromAccount.setBalance(fromAccount.getBalance().subtract(BigDecimal.valueOf(trValue)));
            toAccount.setBalance(toAccount.getBalance().add(BigDecimal.valueOf(trValue)));
            em.merge(fromAccount);
            em.merge(toAccount);

            em.persist(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public AccountTransaction update(AccountTransaction accountTransaction) {
		return em.merge(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(AccountTransaction accountTransaction) {
            accountTransaction.setInvis(Boolean.TRUE);
            this.update(accountTransaction);
		//em.remove(accountTransaction);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(BigDecimal id) {
		AccountTransaction accountTransaction = em.getReference(AccountTransaction.class, id);
                accountTransaction.setInvis(Boolean.TRUE);
                this.update(accountTransaction);
		//delete(accountTransaction);
	}    
}
