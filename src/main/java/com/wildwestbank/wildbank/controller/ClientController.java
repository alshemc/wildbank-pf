package com.wildwestbank.wildbank.controller;

import java.util.List;

import com.wildwestbank.wildbank.jsf.model.Client;

/**
 * @author Alesha
 */
public interface ClientController {

	/**
	 * @return mock list of codes
	 */
	List<Client> list();

	/**
	 * Reads a {@link Client}
	 * 
	 * @param id
	 * @return
	 */
	Client read(Integer id);

	/**
	 * Creates a new {@link Client}
	 * 
	 * @param client
	 */
	void create(Client client);

	/**
	 * Updates a {@link Client} and returns the updated one
	 * 
	 * @param client
	 * @return
	 */
	Client update(Client client);

	/**
	 * Deletes a {@link Client}.
	 * 
	 * @param client
	 */
	void delete(Client client);

	/**
	 * Deletes a {@link Client}.
	 * 
	 * @param id
	 */
	void delete(Integer id);


}