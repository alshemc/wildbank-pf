package com.wildwestbank.wildbank.repository.impl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.repository.ClientRepository;

/**
 * @author Alesha
 */
@Named
public class ClientRepositoryImpl implements ClientRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public List<Client> list() {
            /*
            SELECT x.*, SUM(ac.BALANCE) as BALANCE FROM CLIENT x
            RIGHT JOIN Account ac ON x.CLIENT_ID = ac.CLIENT_ID
            WHERE (x.INVIS IS NULL) OR (x.INVIS <> TRUE) ORDER BY x.CLIENT_ID ASC
            */
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //, SUM(ac.BALANCE) AS currentBalance ") 
				.append("FROM " + Client.class.getName() + " x ") //
                                //.append("JOIN x.accountCollection ac " ) 
                                .append("WHERE (INVIS IS NULL) OR (INVIS <> TRUE)")
                                .append("ORDER BY x.id ASC ");

		return em.createQuery(jpql.toString(), Client.class).getResultList();
	}

	@Override
	public Client read(Integer id) {
		return em.find(Client.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void create(Client client) {
            client.setClientDate(new java.util.Date());
		em.persist(client);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Client update(Client client) {
		return em.merge(client);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(Client client) {
            client.setInvis(Boolean.TRUE);
            this.update(client);
		//em.remove(client);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(Integer id) {
		Client client = em.getReference(Client.class, id);
                client.setInvis(Boolean.TRUE);
                this.update(client);
		//delete(client);
	}

}