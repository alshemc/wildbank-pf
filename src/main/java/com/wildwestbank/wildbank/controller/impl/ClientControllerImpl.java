package com.wildwestbank.wildbank.controller.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wildwestbank.wildbank.controller.ClientController;
import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.repository.ClientRepository;

/**
 * @author Alesha
 */
@Named
public class ClientControllerImpl implements ClientController {

	@Inject
	private ClientRepository repository;

	@Override
	public List<Client> list() {
		return repository.list();
	}

	@Override
	public Client read(Integer id) {
		return repository.read(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Client client) {
		repository.create(client);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Client update(Client country) {
		return repository.update(country);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Client country) {
		repository.delete(country);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		repository.delete(id);
	}

}