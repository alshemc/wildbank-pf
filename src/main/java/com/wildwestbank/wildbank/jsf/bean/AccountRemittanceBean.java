/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.bean;

import com.wildwestbank.wildbank.controller.AccountController;
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
import com.wildwestbank.wildbank.jsf.model.Account;
import com.wildwestbank.wildbank.jsf.model.Client;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alesha
 */
@ManagedBean
@ViewScoped
public class AccountRemittanceBean extends SpringBeanAutowiringSupport implements Serializable {
	protected static final Log log = LogFactory.getLog(AccountRemittanceBean.class);

	@Inject
	private AccountTransactionController atController;

        @Inject
	private AccountController accController;
                
        @ManagedProperty("#{clientAccountsBean}")
        private ClientAccountsBean activeAccount;
        
        private String state;
        private AccountTransaction item;
        private Date filterDateFrom;
        private Date filterDateTo;
        private boolean filterCheckFrom;
        private boolean filterCheckTo;

        private Client destinationClient; 
        private List<Account> destinationAccounts;
        private Map<Integer,List<Account>> allClientsAccounts = new HashMap<Integer,List<Account>>();
        
	/**
	 * 
	 */
	public AccountRemittanceBean() {
		log.info("AccountRemittanceBean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	public void postConstruct() {
		log.info("AccountRemittanceBean @PostConstruct called.");
		state = "READ";
                
                if (activeAccount!=null)
                    for (Client cl: activeAccount.getActiveClient().getItems()){
                        destinationAccounts = accController.listByClient(cl.getClientId());
                        allClientsAccounts.put(cl.getClientId(), destinationAccounts);
                    }
                else
                    log.info("activeAccount IS NULL in PostConstruct");
                
                destinationAccounts = null;
	}

	/**
	 * Clears entity items
	 */
	public void clearItems() {
		//if (items != null) {
		//	items.clear();
		//}
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
            try{
                item.setTrDate(getNowDate());
                atController.create(item);
                
            } catch (Exception e) {
                    log.info("PersistenceErrorOccured in AccountRemittanceBean.create() \n" + e.toString());
            }
            //items = controller.list();
            item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
		atController.update(item);
		//items = controller.list();
		item = null;
	}

	public void delete() {
		atController.delete(item.getAccounttrId());
		//items = controller.list();
		item = null;
	}

        public String prepareCreate() {
            item = new AccountTransaction();
            return "CreateRemit";
        }
        
	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("AccountRemittanceBean @PreDestroy called.");
	}
                
        public void onClientChange() {
            if(destinationClient !=null && !destinationClient.equals(0))
                destinationAccounts = allClientsAccounts.get(destinationClient.getClientId());
            else
                destinationAccounts = new ArrayList<Account>();
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

	public AccountTransaction getItem() {
		return item;
	}

	public void setItem(AccountTransaction item) {
		this.item = item;
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
        
        public ClientAccountsBean getActiveAccount(){
            return activeAccount;
        }
        
        public void setActiveAccount(ClientAccountsBean activeAccount){
            this.activeAccount = activeAccount;
        }
        
        public Client getDestinationClient(){
            return destinationClient;
        }
        
        public void setDestinationClient(Client client){
            this.destinationClient = client;
        }
        
        public List<Account> getDestinationAccounts(){
            return destinationAccounts;
        }
        
        public void setDestinationAccounts(List<Account> list){
            this.destinationAccounts = list;
        }
        
        public Map<Integer,List<Account>> getAllClientsAccounts(){
            return allClientsAccounts;
        }
        
        public Date getNowDate() throws InstantiationException, IllegalAccessException{
            return Date.class.newInstance();
        }
        
        public void validateTrValue(FacesContext context, UIComponent component, Object value) throws ValidatorException{
            log.info("accountRemittanceBean - inside validateTrValue()");

            if (value == null )
            {
                log.info("Value is null");
                return;
            };
            
            Account fromAccount = (Account) component.getAttributes().get("fromAccountBalance");
            BigDecimal accountBalance = accController.read(fromAccount.getAccountId()).getBalance();
            
            log.info("accountId: " + fromAccount.toString() );
            //log.info("accountBalance: " + accountBalance.toString() );
            //log.info("Value: " + value.getClass().toString() +"  " + value.toString() );
            
            Double trValue = Double.valueOf(value.toString());
            if ( trValue <= 0 )
            {
                log.info("Validation failed! Transaction Value incorrect...");
                FacesUtils.addI18nError("message.validate.trvalue+");
                throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
            }
            
            log.info("accountBalance: " + accountBalance.toString() );
            log.info("trValue: " + trValue.toString() );

            if ( accountBalance.compareTo(BigDecimal.valueOf(trValue)) < 0 )
            {
                log.info("Validation failed! accountBalance smaller then transaction Value...");
                /*ResourceBundle bundle = ResourceBundle.getBundle("com.wildwestbank.wildbank.jsf", Locale.ENGLISH);
                String label = bundle.getString ("accountBalance.smaller");
                FacesMessage message = new FacesMessage(label);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);*/
                FacesUtils.addI18nError("accountBalance.smaller");
                throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
            }

        }
        
}
