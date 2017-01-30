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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTimeComparator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Alesha
 */
@ManagedBean
@ViewScoped
//@SessionScoped
public class ClientAccountsBean extends SpringBeanAutowiringSupport implements Serializable {
	protected static final Log log = LogFactory.getLog(ClientAccountsBean.class);

	@Inject
	private AccountController controller;
        
        //@ManagedProperty("#{selectedClientId}")
        private Integer selectedClientId;

        @ManagedProperty("#{clientBean}")
        private ClientBean activeClient;
        
        private String state;
	private List<Account> items;
	private Account item;
	private Account selectedAccount;
        private int rowCount;
        
        private String code;
        private Date dateBegin;
        private Date dateEnd;
        private BigDecimal balance;
        
	/**
	 * 
	 */
	public ClientAccountsBean() {
		log.info("ClientAccountsBean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	private void postConstruct() {
		log.info("ClientAccountsBean @PostConstruct called.");
		state = "READ";

                FacesContext facesContext = FacesContext.getCurrentInstance();
                log.info("RequestParameterMap is ... " + facesContext.getExternalContext().getRequestParameterMap().toString());
                String foo = facesContext.getExternalContext().getRequestParameterMap().get("client_id");
                log.info(foo);
                if (foo != null) this.selectedClientId = Integer.parseInt(foo);
                if (this.selectedClientId!=null) {
                    log.info("ClientID = "+Integer.toString(this.selectedClientId));
                    this.prepareList();
                }else {
                    items = null;
                    log.info("ClientId is null in ManagedProperty!");
                    //log.info("ClientId = " + selectedClientId.toString());
                }

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
                //fixDates();
		controller.create(item);
		items = controller.listByClient(this.selectedClientId);
		item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
                //fixDates();
		controller.update(item);
		items = controller.listByClient(this.selectedClientId);
		item = null;
	}

	public void delete() {
            
            if ( !this.haveTransactions() ){
                    controller.delete(item.getAccountId());
                    items = controller.listByClient(this.selectedClientId);
                    item = null;
            }else{
                    FacesUtils.addI18nError("message.cannot.delete.account");
                    //throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
            }
            
	}

        public void prepareList() {
            this.selectedClientId = activeClient.getItem().getClientId();
            items = controller.listByClient(this.selectedClientId);
            item = null;
	}
        
        public boolean haveTransactions(){
            
            if (this.item!=null)
                return (item.getNumberTransactions()>0);
            else
                return false;
        }
        
        /**
	 * @param event
	 */
	public void add() {
            try {
                if (item == null){
                    item = Account.class.newInstance();
                    log.info("Account class item is null!");
                }
                if (activeClient == null){
                    log.info("activeClient is null! Trubble!");
                }else
                    item.setClientId(activeClient.read(selectedClientId));
                log.info(this.code+this.dateBegin.toString()+this.dateEnd.toString()+this.balance.toString());
                //item.setAccountCode(this.code);
                //item.setDateBegin(this.dateBegin);
                //item.setDateEnd(this.dateEnd);
                //item.setBalance(this.balance);
                //this.create();
            } catch (InstantiationException | IllegalAccessException e) {
                FacesUtils.addI18nError("generic.bean.unableToCleanViewData");
            } catch (NullPointerException ex){
                log.info(ex.toString());
            } finally {
                log.info(this.code+this.dateBegin.toString()+this.dateEnd.toString()+this.balance.toString());
            }
        }
        
        public void displayCreateAccount(){
            FacesMessage msg;
            if(selectedClientId != null)
                msg = new FacesMessage("Selected ", selectedClientId + " of clients" );
            else
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "ClientId is not selected!"); 

            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }
        
        private String validateDates() {
            FacesContext context = FacesContext.getCurrentInstance();
            if ( !item.getDateBegin().before(item.getDateEnd()) ) {
                item.setDateEnd(null);
                //FacesMessage errorMessage = new FacesMessage("End date must be after start date");
                //errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                //context.addMessage(null, errorMessage);
                FacesUtils.addI18nError("message.validate.dateend");
                return(null);
            } else {
                return("show-dates");
            }
        }

        private void fixDates() {
            if ( item != null) {
                item.setDateBegin(this.dateBegin);
                item.setDateEnd(this.dateEnd);
            }
        }
        
        public void beginDateSelect(SelectEvent event){
           log.info("ClientAccountsBean - inside beginDateSelect() SelectEvent");

           System.out.println("begin date: " + this.item.getDateBegin());
           System.out.println("end date: " + this.item.getDateEnd());

           if (this.item.getDateEnd()!=null) {
                Calendar calStart = Calendar.getInstance();
                calStart.setTime(this.item.getDateBegin());

                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(this.item.getDateEnd());

                if ( DateTimeComparator.getDateOnlyInstance().compare(calStart, calEnd) > 0 )
                {
                    log.info("Validation failed");
                    //FacesContext facesContext = FacesContext.getCurrentInstance();
                    //SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "End date is before begin date", format.format(event.getObject())));
                    FacesUtils.addI18nError("message.validate.datestart" );
                }
                
           }
        }
                
        public void beginDateFilter(AjaxBehaviorEvent event){
           System.out.println("ClientAccountsBean - inside startDateFilter() AjaxBehaviorEvent");

           System.out.println("start date: " + this.item.getDateBegin());
           System.out.println("end date: " + this.item.getDateEnd());

           Calendar calStart = Calendar.getInstance();
           calStart.setTime(this.item.getDateBegin());

           Calendar calEnd = Calendar.getInstance();
           calEnd.setTime(this.item.getDateEnd());

           if ( DateTimeComparator.getDateOnlyInstance().compare(calStart, calEnd) > 0 )
           {
               System.out.println("Validation failed");
               //FacesMessage message = new FacesMessage("AjaxEvent - Start date is after End date");
               //message.setSeverity(FacesMessage.SEVERITY_ERROR);
               //throw new ValidatorException(message);
               FacesUtils.addI18nError("message.validate.datestart");
               throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
           }
        }
        
        public void endDateSelect(SelectEvent event) throws InstantiationException, IllegalAccessException {
           log.info("ClientAccountsBean - inside endDateSelect() DateSelectEvent");

           log.info("begin date: " + this.item.getDateBegin());
           log.info("end date: " + this.item.getDateEnd());
           if (this.item.getDateBegin()!=null){
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(this.item.getDateEnd());

                Calendar calStart = Calendar.getInstance();
                calStart.setTime(this.item.getDateBegin());

                if ( DateTimeComparator.getDateOnlyInstance().compare(calStart, calEnd) > 0 )
                {
                    log.info("Validation failed");
                    //FacesContext facesContext = FacesContext.getCurrentInstance();
                    //SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "End date is before begin date", format.format(event.getObject())));
                    FacesUtils.addI18nError("message.validate.dateend");
                }
                
                Calendar calCurrent = Calendar.getInstance();
                calCurrent.setTime(Date.class.newInstance());
                if ( DateTimeComparator.getDateOnlyInstance().compare(calCurrent, calEnd) > 0 )
                {
                    log.info("Validation current date failed");
                    FacesUtils.addI18nError("message.validate.datecur");
                }
           }
       }
       public void endDateFilter(AjaxBehaviorEvent event) {
           log.info("ClientAccountsBean - inside endDateFilter() AjaxBehaviorEvent");

           log.info("start date: " + this.dateBegin);
           log.info("end date: " + this.dateEnd);

           Calendar calStart = Calendar.getInstance();
           calStart.setTime(this.dateBegin);

           Calendar calEnd = Calendar.getInstance();
           calEnd.setTime(this.dateEnd);

           if ( DateTimeComparator.getDateOnlyInstance().compare(calStart, calEnd) > 0 )
           {
               log.info("Validation failed");
               //FacesMessage message = new FacesMessage("AjaxEvent - End date is before start date");
               //message.setSeverity(FacesMessage.SEVERITY_ERROR);
               //throw new ValidatorException(message);
               FacesUtils.addI18nError("message.validate.datestart");
               throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
           }
       }
        
	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("ClientAccountsBean @PreDestroy called.");
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

        public Account getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(Account selectedAccount) {
            //log.info("Account selectedAccount ID is: " + selectedAccount.getAccountId());
            this.selectedAccount = selectedAccount;
	}

        
        public Integer getSelectedClientId() {
		return selectedClientId;
	}

	public void setSelectedClientId(Integer selectedClientId) {
		this.selectedClientId = selectedClientId;
	}
        
        public ClientBean getActiveClient() {
		return activeClient;
	}

	public void setActiveClient(ClientBean activeClient) {
		this.activeClient = activeClient;
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
        
        public Date getNowDate() throws InstantiationException, IllegalAccessException{
            return Date.class.newInstance();
        }
        
    @FacesConverter(value = "accountConverter")
    public static class AccountConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            //return controller.read(getKey(value));
            ClientAccountsBean accountBean = (ClientAccountsBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clientAccountsBean");
            return accountBean.controller.read(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            try {
                key = Long.valueOf(value);
                return key;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(value + " is not a valid Account ID"), e);
            }
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Account) {
                Account o = (Account) object;
                return getStringKey(o.getAccountId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Account.class.getName()});
                return null;
            }
        }
    }
    
}
