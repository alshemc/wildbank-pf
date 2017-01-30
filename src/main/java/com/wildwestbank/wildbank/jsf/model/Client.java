/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Formula;

/**
 *
 * @author Alesha
 */
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByClientId", query = "SELECT c FROM Client c WHERE c.clientId = :clientId"),
    @NamedQuery(name = "Client.findByClientCode", query = "SELECT c FROM Client c WHERE c.clientCode = :clientCode"),
    @NamedQuery(name = "Client.findByClientName", query = "SELECT c FROM Client c WHERE c.clientName = :clientName"),
    @NamedQuery(name = "Client.findByClientAddress", query = "SELECT c FROM Client c WHERE c.clientAddress = :clientAddress"),
    @NamedQuery(name = "Client.findByClientDate", query = "SELECT c FROM Client c WHERE c.clientDate = :clientDate"),
    @NamedQuery(name = "Client.findByInvis", query = "SELECT c FROM Client c WHERE c.invis = :invis")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLIENT_ID")
    private Integer clientId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "CLIENT_CODE")
    private String clientCode;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CLIENT_NAME")
    private String clientName;
    
    @Size(max = 512)
    @Column(name = "CLIENT_ADDRESS")
    private String clientAddress;
    
    @NotNull
    @Column(name = "CLIENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date clientDate;

    @Column(name = "CLIENT_BIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date clientBirthdate;
    
    @Column(name = "INVIS")
    private Boolean invis;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<Account> accountCollection;

    @Formula("(SELECT SUM(ac.BALANCE) FROM ACCOUNT ac WHERE ac.CLIENT_ID = CLIENT_ID)")
    private BigDecimal currentBalance;
    
    /**
     * Default no-arg constructor
     */
    public Client() {
    }

    /**
     * @param clientId
     */
    public Client(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * @param clientCode
     * @param clientName
     * @param clientAddress
     * @param clientDate
     */
    public Client(String clientCode, String clientName, String clientAddress, Date clientDate) {
        this.clientCode = clientCode;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientDate = clientDate;
    }
    
    /**
     * @return
     */
    public Integer getClientId() {
        return clientId;
    }
    /**
     * @param clientId
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    /**
     * @return
     */
    public String getClientCode() {
        return clientCode;
    }
    /**
     * @param clientCode
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    /**
     * @return
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * @param clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * @return
     */
    public String getClientAddress() {
        return clientAddress;
    }
    /**
     * @param clientAddress
     */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    /**
     * @return
     */
    public Date getClientDate() {
        return clientDate;
    }
    /**
     * @param clientDate
     */
    public void setClientDate(Date clientDate) {
        this.clientDate = clientDate;
    }
    /**
     * @return
     */
    public Date getClientBirthdate() {
        return clientBirthdate;
    }
    /**
     * @param clientBirthdate
     */
    public void setClientBirthdate(Date clientBirthdate) {
        this.clientBirthdate = clientBirthdate;
    }
    /**
     * @return
     */
    public Boolean getInvis() {
        return invis;
    }
    /**
     * @param invis
     */
    public void setInvis(Boolean invis) {
        this.invis = invis;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    public BigDecimal getCurrentBalance(){
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance){
        this.currentBalance = currentBalance;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wildwestbank.wildbank.Client[ clientId=" + clientId + " ]";
    }
    
}
