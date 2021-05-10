package com.example.jaxwshibernate.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ReceivableForAddRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceivableForAddRequest {

    @XmlElement(required = true)
    private String dueDate;

    @XmlElement(required = true)
    private Float priceToPay;

    @XmlElement(required = true)
    private Integer installationId;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Float getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(Float priceToPay) {
        this.priceToPay = priceToPay;
    }

    public Integer getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Integer installationId) {
        this.installationId = installationId;
    }
}
