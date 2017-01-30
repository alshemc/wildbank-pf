/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.bean;

import com.wildwestbank.wildbank.jsf.util.FacesUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.wildwestbank.wildbank.jsf.model.AccountTransaction;
import com.wildwestbank.wildbank.controller.AccountTransactionController;
import com.wildwestbank.wildbank.controller.ClientController;
import com.wildwestbank.wildbank.jsf.model.Client;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Alesha
 */
@ManagedBean
@ViewScoped
public class AccountTransactionBean extends SpringBeanAutowiringSupport implements Serializable {
	protected static final Log log = LogFactory.getLog(AccountTransactionBean.class);

	@Inject
	private AccountTransactionController atrController;

	@Inject
	private ClientController clController;
        
        private String state;
	private List<AccountTransaction> items;
        private List<AccountTransaction> filteredTransactions;
        private List<Client> clients;
	private AccountTransaction item;
        
        private Date filterDateFrom;
        private Date filterDateTo;
        private boolean filterCheckFrom;
        private boolean filterCheckTo;
        private boolean filterClientBool;
        private Client filterClient; 
        
	/**
	 * 
	 */
	public AccountTransactionBean() {
		log.info("AccountTransactionBean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	private void postConstruct() {
		log.info("AccountTransactionBean @PostConstruct called.");
		state = "READ";
                prepareList();
                clients = clController.list();
		//items = controller.list();
	}

	/**
	 * Clears entity items
	 */
	public void clearItems() {
		if (items != null) {
			items.clear();
		}
	}

	/**
	 * Clears entity item
	 */
	public void clearItem() {
		try {
			// Instantiating via reflection was used here for generic purposes
			item = AccountTransaction.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			FacesUtils.addI18nError("generic.bean.unableToCleanViewData");
		}
	}

	/**
	 * @param event
	 */
	public void create() {
		atrController.create(item);
                prepareList();
		//items = controller.list();
		item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
		atrController.update(item);
		items = atrController.list();
		item = null;
	}

	public void delete() {
		atrController.delete(item.getAccounttrId());
		items = atrController.list();
		item = null;
	}

	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("AccountTransactionBean @PreDestroy called.");
	}

        public void prepareList() {
            
            Date dateFrom = null;
            Date dateTo = null;
            Client selectedClient = null;
            if ( filterCheckFrom & (filterDateFrom!=null) )
                dateFrom = filterDateFrom;
            if ( filterCheckTo & (filterDateTo!=null))
                dateTo = filterDateTo;
            if ( filterClientBool & (filterClient!=null) )
                selectedClient = filterClient;
            
            items = atrController.listByFilter(dateFrom, dateTo, selectedClient);
            item = null;
	}
        
	/*
	 * Getters and Setters
	 */
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<AccountTransaction> getItems() {
		return items;
	}

	public void setItems(List<AccountTransaction> items) {
		this.items = items;
	}

	public AccountTransaction getItem() {
		return item;
	}

	public void setItem(AccountTransaction item) {
		this.item = item;
	}

	public List<AccountTransaction> getFilteredTransactions() {
		return filteredTransactions;
	}

	public void setFilteredTransactions(List<AccountTransaction> filteredTransactions) {
		this.filteredTransactions = filteredTransactions;
	}
        
        public Date getFilterDateFrom(){
            return filterDateFrom;
        }
        
        public void setFilterDateFrom(Date filterDateFrom){
            this.filterDateFrom = filterDateFrom;
        }

        public Date getFilterDateTo(){
            return filterDateTo;
        }
        
        public void setFilterDateTo(Date filterDateTo){
            this.filterDateTo = filterDateTo;
        }
        
        public boolean getFilterCheckFrom(){
            return filterCheckFrom;
        }
        
        public void setFilterCheckFrom(boolean filterCheckFrom){
            this.filterCheckFrom = filterCheckFrom;
        }

        public boolean getFilterCheckTo(){
            return filterCheckTo;
        }
        
        public void setFilterCheckTo(boolean filterCheckTo){
            this.filterCheckTo = filterCheckTo;
        }
        
        public boolean getFilterClientBool(){
            return filterClientBool;
        }
        
        public void setFilterClientBool(boolean filterClientBool){
            this.filterClientBool = filterClientBool;
        }
        
        public  Client getFilterClient(){
           return filterClient;
        }
        
        public void setFilterClient(Client filterClient){
            this.filterClient = filterClient;
        }
        
        public List<Client> getClients(){
            return clients;
        }
        
        public void setClients(List<Client> clients){
            this.clients = clients;
        }
        
        public boolean filterByValue(Object value, Object filter, Locale locale) {
            String filterText = (filter == null) ? null : filter.toString().trim();
            if(filterText == null||filterText.equals("")) {
                return true;
            }

            if(value == null) {
                return false;
            }

            return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
        }
        
}
