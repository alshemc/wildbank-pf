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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Formula;

/**
 *
 * @author Alesha
 */
@Entity
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByAccountCode", query = "SELECT a FROM Account a WHERE a.accountCode = :accountCode"),
    @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance"),
    @NamedQuery(name = "Account.findByInvis", query = "SELECT a FROM Account a WHERE a.invis = :invis"),
    @NamedQuery(name = "Account.findByClientId", query = "SELECT a FROM Account a WHERE a.clientId = :clientId")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Min(value=0)
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @NotNull
    @Column(name = "DATE_BEGIN")
    @Temporal(TemporalType.DATE)
    private Date dateBegin;
    
    @Column(name = "DATE_END")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    
    @Column(name = "INVIS")
    private Boolean invis;
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @ManyToOne(optional = false)
    private Client clientId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountFrom")
    private Collection<AccountTransaction> accountTransactionFromCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountTo")
    private Collection<AccountTransaction> accountTransactionToCollection;

    @Formula("(SELECT COUNT(at.*) FROM ACCOUNT_TRANSACTION at WHERE (at.ACCOUNT_FROM = ACCOUNT_ID)OR(at.ACCOUNT_TO = ACCOUNT_ID))")
    private Integer numberTransactions;
    
    /**
     * Default no-arg constructor
     */
    public Account() {
    }
    /**
     * @param accountId
     */
    public Account(Long accountId) {
        this.accountId = accountId;
    }
    /**
     * @param accountId
     * @param accountCode
     * @param balance
     */
    public Account(Long accountId, String accountCode, BigDecimal balance) {
        this.accountId = accountId;
        this.accountCode = accountCode;
        this.balance = balance;
    }
    /**
     * @return
     */
    public Long getAccountId() {
        return accountId;
    }
    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Boolean getInvis() {
        return invis;
    }

    public void setInvis(Boolean invis) {
        this.invis = invis;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    @XmlTransient
    public Collection<AccountTransaction> getAccountTransactionFromCollection() {
        return accountTransactionFromCollection;
    }

    public void setAccountTransactionFromCollection(Collection<AccountTransaction> accountTransactionCollection) {
        this.accountTransactionFromCollection = accountTransactionCollection;
    }

    @XmlTransient
    public Collection<AccountTransaction> getAccountTransactionToCollection() {
        return accountTransactionToCollection;
    }

    public void setAccountTransactionToCollection(Collection<AccountTransaction> accountTransactionCollection) {
        this.accountTransactionToCollection = accountTransactionCollection;
    }

    public Integer getNumberTransactions(){
        return numberTransactions;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wildwestbank.wildbank.Account[ accountId=" + accountId + " ]";
    }
    
}
