package com.wildwestbank.wildbank.listener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.jsf.model.Client;
import java.sql.Date;

/**
 * Spring application context loader that checks if database has initial data.
 * If not, it fills database with some mock data.
 * 
 * @author Alesha
 */
@Named
public class ApplicationLoaderListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void onApplicationEvent(ContextRefreshedEvent event) {
		checkDatabase();
	}

	private void checkDatabase() {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT COUNT(x) ") //
				.append("FROM " + Client.class.getSimpleName() + " x ");

		TypedQuery<Number> query = em
				.createQuery(jpql.toString(), Number.class);

		long count = query.getSingleResult().longValue();
		if (count == 0) {
			initializeDatabaseWithMockData();
		}
	}

	/**
	 * 
	 */
	private void initializeDatabaseWithMockData() {
		List<Client> clients = Arrays.asList( //
                        new Client("78000001","First Name", "First Adsress", (new java.util.Date())), //
				new Client("78000002","Second Name", "Second Adsress", (new java.util.Date())), //
				new Client("78000003","Third Name", "Third Adsress", (new java.util.Date())), //
				new Client("78000004","Fourth Name", "Fourth Adsress", (new java.util.Date())), //
				new Client("78000005","Fifth Name", "Fifth Adsress", (new java.util.Date())));

		for (Client client : clients) {
			em.persist(client);
		}
	}

}