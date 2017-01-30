/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alesha
 */
@Entity
@Table(name = "ACCOUNT_TRANSACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountTransaction.findAll", query = "SELECT a FROM AccountTransaction a"),
    @NamedQuery(name = "AccountTransaction.findByAccounttrId", query = "SELECT a FROM AccountTransaction a WHERE a.accounttrId = :accounttrId"),
    @NamedQuery(name = "AccountTransaction.findByTrValue", query = "SELECT a FROM AccountTransaction a WHERE a.trValue = :trValue"),
    @NamedQuery(name = "AccountTransaction.findByTrDate", query = "SELECT a FROM AccountTransaction a WHERE a.trDate = :trDate"),
    @NamedQuery(name = "AccountTransaction.findByInvis", query = "SELECT a FROM AccountTransaction a WHERE a.invis = :invis")})
public class AccountTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNTTR_ID")
    private BigDecimal accounttrId;
    
    //@NotNull
    @Column(name = "TR_DATE")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date trDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TR_VALUE")
    private double trValue;
    
    @JoinColumn(name = "ACCOUNT_FROM", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountFrom;
    
    private String clientNameFrom;
    
    @JoinColumn(name = "ACCOUNT_TO", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountTo;
    
    private String clientNameTo;
    
    @Column(name = "INVIS")
    private Boolean invis;
        
    /**
     * Default no-arg constructor
     */
    public AccountTransaction() {
    }
    /**
     * @param accounttrId
     */
    public AccountTransaction(BigDecimal accounttrId) {
        this.accounttrId = accounttrId;
    }
    /**
     * @param accounttrId
     * @param trValue
     * @param trDate
     */
    public AccountTransaction(BigDecimal accounttrId, double trValue, Date trDate) {
        this.accounttrId = accounttrId;
        this.trValue = trValue;
        this.trDate = trDate;
    }

    public BigDecimal getAccounttrId() {
        return accounttrId;
    }

    public void setAccounttrId(BigDecimal accounttrId) {
        this.accounttrId = accounttrId;
    }

    public double getTrValue() {
        return trValue;
    }

    public void setTrValue(double trValue) {
        this.trValue = trValue;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Date getTrDate() {
        return trDate;
    }

    public void setTrDate(Date trDate) {
        this.trDate = trDate;
    }

    public Boolean getInvis() {
        return invis;
    }

    public void setInvis(Boolean invis) {
        this.invis = invis;
    }

    public String getClientNameFrom(){
        return clientNameFrom;
    }

    public void setClientNameFrom(String clientNameFrom){
        this.clientNameFrom = clientNameFrom;
    }

    public String getClientNameTo(){
        return clientNameTo;
    }
    
    public void setClientNameTo(String clientNameTo){
        this.clientNameTo = clientNameTo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accounttrId != null ? accounttrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountTransaction)) {
            return false;
        }
        AccountTransaction other = (AccountTransaction) object;
        if ((this.accounttrId == null && other.accounttrId != null) || (this.accounttrId != null && !this.accounttrId.equals(other.accounttrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wildwestbank.wildbank.AccountTransaction[ accounttrId=" + accounttrId + " ]";
    }
    
}
