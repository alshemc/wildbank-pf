package com.wildwestbank.wildbank.jsf.bean;

import com.wildwestbank.wildbank.controller.AccountController;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.wildwestbank.wildbank.controller.ClientController;
import com.wildwestbank.wildbank.jsf.model.Client;
import com.wildwestbank.wildbank.jsf.util.FacesUtils;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

/**
 * Making the JSF bean extend {@link SpringBeanAutowiringSupport} is the best
 * way to seamlessly integrate JSF Context with Spring Context, enabling
 * features like DI via @{@link Inject} and @{@link Autowire} . Spring does not
 * provide a powerful built-in JSF integration module.
 * 
 * @author Alesha
 */
@ManagedBean
//@ViewScoped
@SessionScoped
public class ClientBean extends SpringBeanAutowiringSupport implements Serializable{

	protected static final Log log = LogFactory.getLog(ClientBean.class);

	@Inject
	private ClientController clController;

        @Inject
	private AccountController acController;
        
	private String state;
	private List<Client> items;
	private Client item;
        private int rowCount;

	/**
	 * 
	 */
	public ClientBean() {
		log.info("ClientBean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	private void postConstruct() {
		log.info("ClientBean @PostConstruct called.");
		state = "READ";
		prepareList();
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
			item = Client.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			FacesUtils.addI18nError("generic.bean.unableToCleanViewData");
		}
	}

	/**
	 * @param event
	 */
	public void create() {
		clController.create(item);
		prepareList();
		item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
		clController.update(item);
		prepareList();
		item = null;
	}

	public void delete() {//throws ValidatorException {
            if ( item!=null ){
		if ( acController.listByClient(item.getClientId()).size()==0 ){
                    clController.delete(item.getClientId());
                    prepareList();
                    item = null;
                }else{
                    FacesUtils.addI18nError("message.cannot.delete.client");
                    //throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
                }
            }
	}

	public Client read(Integer Id) {
            return clController.read(Id);
	}
        
        public void prepareList() {
            items = clController.list();
	}
        
	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("ClientBean @PreDestroy called.");
	}

        public void clientViewListener(ActionEvent actionEvent) {
            Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            log.info(sessionMap.toString());
            AccountBean bean= (AccountBean)sessionMap.get("accountBean");
            /*if (bean != null){
                bean.setSelectedClient(this);
                if (bean.getSelectedClient()!=null)
                    log.info(bean.getSelectedClient().item.getClientId().toString());
                else
                    log.info("Client not selected in AccountBean now!");
            } else {
                log.info("AccountBean not created now!");
            }*/
            
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

	public List<Client> getItems() {
		return items;
	}

	public void setItems(List<Client> items) {
		this.items = items;
	}

	public Client getItem() {
		return item;
	}

	public void setItem(Client item) {
		this.item = item;
	}

        public int getRowCount() {
            rowCount = items.size();
            return rowCount;
        }

    @FacesConverter(value = "clientConverter")
    public static class AccountConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            // This will return the actual object representation
            // of your Client using the value returned from the client side
            if (value == null || value.length() == 0) {
                return null;
            }
            ClientBean clientBean = (ClientBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clientBean");
            return clientBean.clController.read(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            try {
                key = Integer.valueOf(value);
                return key;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(value + " is not a valid Client ID"), e);
            }
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Client) {
                Client o = (Client) object;
                return getStringKey(o.getClientId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Client.class.getName()});
                return null;
            }
        }
        
        /*@Override
        public Client getAsObject(FacesContext context, UIComponent component, String submittedValue) {

            if (submittedValue == null || submittedValue.isEmpty()) {
                return null;
            }
            try {
                return clController.read(Integer.valueOf(submittedValue));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Client ID"), e);
            }
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return "";
            }

            if (value instanceof Client) {
                return String.valueOf(((Client) value).getClientId());
            } else {
                throw new ConverterException(new FacesMessage(value + " is not a valid Client"));
            }
        }*/

        
    }
}