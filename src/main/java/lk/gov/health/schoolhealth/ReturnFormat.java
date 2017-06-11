/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.schoolhealth;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author User
 */
@Entity
public class ReturnFormat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    String name;
    String code;
    @Enumerated(EnumType.STRING)
    ReturnTimeFrequency frequency;
    @ManyToOne
    Item category;
    Integer senderStartDays;
    Integer senderSubmitDays;
    Integer sendingDeadline;
    AreaType sendingAreaType;
    PrivilegeType prepairedBy;
    PrivilegeType sentBy;
    PrivilegeType receivedBy;
    Integer receiveStartDays;
    Integer receiveSubmitDays;
    Integer receiveDeadline;
    AreaType receivingAreaType;
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReturnTimeFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(ReturnTimeFrequency frequency) {
        this.frequency = frequency;
    }

    public Item getCategory() {
        return category;
    }

    public void setCategory(Item category) {
        this.category = category;
    }

    public Integer getSenderStartDays() {
        return senderStartDays;
    }

    public void setSenderStartDays(Integer senderStartDays) {
        this.senderStartDays = senderStartDays;
    }

    public Integer getSenderSubmitDays() {
        return senderSubmitDays;
    }

    public void setSenderSubmitDays(Integer senderSubmitDays) {
        this.senderSubmitDays = senderSubmitDays;
    }

    public Integer getSendingDeadline() {
        return sendingDeadline;
    }

    public void setSendingDeadline(Integer sendingDeadline) {
        this.sendingDeadline = sendingDeadline;
    }

    public PrivilegeType getPrepairedBy() {
        return prepairedBy;
    }

    public void setPrepairedBy(PrivilegeType prepairedBy) {
        this.prepairedBy = prepairedBy;
    }

    public PrivilegeType getSentBy() {
        return sentBy;
    }

    public void setSentBy(PrivilegeType sentBy) {
        this.sentBy = sentBy;
    }

    public PrivilegeType getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(PrivilegeType receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Integer getReceiveStartDays() {
        return receiveStartDays;
    }

    public void setReceiveStartDays(Integer receiveStartDays) {
        this.receiveStartDays = receiveStartDays;
    }

    public Integer getReceiveSubmitDays() {
        return receiveSubmitDays;
    }

    public void setReceiveSubmitDays(Integer receiveSubmitDays) {
        this.receiveSubmitDays = receiveSubmitDays;
    }

    public Integer getReceiveDeadline() {
        return receiveDeadline;
    }

    public void setReceiveDeadline(Integer receiveDeadline) {
        this.receiveDeadline = receiveDeadline;
    }

    public AreaType getSendingAreaType() {
        return sendingAreaType;
    }

    public void setSendingAreaType(AreaType sendingAreaType) {
        this.sendingAreaType = sendingAreaType;
    }

    public AreaType getReceivingAreaType() {
        return receivingAreaType;
    }

    public void setReceivingAreaType(AreaType receivingAreaType) {
        this.receivingAreaType = receivingAreaType;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReturnFormat)) {
            return false;
        }
        ReturnFormat other = (ReturnFormat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.gov.health.schoolhealth.ReturnType[ id=" + id + " ]";
    }
    
}
