/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.bean;

import com.wildwestbank.wildbank.controller.AccountController;
import com.wildwestbank.wildbank.jsf.model.Account;
import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.jsf.util.FacesUtils;
import com.wildwestbank.wildbank.repository.impl.ClientRepositoryImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Alesha
 */
@ManagedBean
@ViewScoped
//@SessionScoped
public class AccountBean extends SpringBeanAutowiringSupport implements Serializable {
	protected static final Log log = LogFactory.getLog(AccountBean.class);

	@Inject
	private AccountController controller;
        
        private String state;
	private List<Account> items;
        private List<Account> clientAccounts;
	private Account item;
        private int rowCount;
        
        private String code;
        private Date dateBegin;
        private Date dateEnd;
        private BigDecimal balance;
        
	/**
	 * 
	 */
	public AccountBean() {
		log.info("AccountBean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	private void postConstruct() {
		log.info("AccountBean @PostConstruct called.");
		state = "READ";
		items = controller.list();

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
			item = Account.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			FacesUtils.addI18nError("generic.bean.unableToCleanViewData");
		}
	}

	/**
	 * @param event
	 */
	public void create() {
		controller.create(item);
		items = controller.list();
		item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
		controller.update(item);
		items = controller.list();
		item = null;
	}

	public void delete() {
		controller.delete(item.getAccountId());
		items = controller.list();
		item = null;
	}

	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("AccountBean @PreDestroy called.");
	}

        public boolean filterByClientId(Object value, Object filter, Locale locale) {
            String filterText = (filter == null) ? null : filter.toString().trim();
            log.info("Filter TEXT is: " + filterText);
            if(filterText == null||filterText.equals("")) {
                return true;
            }

            if(value == null) {
                log.info("Object VALUE is: NULL");
                return false;
            }
            log.info("Object VALUE is: " + value.toString());
            return ((Comparable) value).compareTo(Integer.valueOf(filterText)) == 0;
        }
        
        public void onPage(ActionEvent actionEvent){
            log.info("!!!SOMETHING WRONG ON PAGE!!!");
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

	public List<Account> getItems() {
		return items;
	}

	public void setItems(List<Account> items) {
		this.items = items;
	}

	public Account getItem() {
		return item;
	}

	public void setItem(Account item) {
		this.item = item;
	}
	public List<Account> getClientAccounts() {
		return clientAccounts;
	}

	public void setClientAccounts(List<Account> clientAccounts) {
		this.clientAccounts = clientAccounts;
	}
       
        public int getRowCount() {
            rowCount = items.size();
            return rowCount;
        }

        public String getCode(){
            return code;
        }
        public void setCode(String code){
            this.code = code;
        }
        public BigDecimal getBalance(){
            return balance;
        }
        public void setBalance(BigDecimal balance){
            this.balance = balance;
        }
        public Date getDateBegin(){
            return dateBegin;
        }
        public void setDateBegin(Date dateBegin){
            this.dateBegin = dateBegin;
        }
        public Date getDateEnd(){
            return dateEnd;
        }
        public void setDateEnd(Date dateEnd){
            this.dateEnd = dateEnd;
        }
}
