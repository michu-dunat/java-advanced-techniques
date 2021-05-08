package com.example.jaxwshibernate.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ReceivableRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceivableRequest {

    @XmlElement(required = true)
    private Integer clientId;

    @XmlElement(required = true)
    private String fromDate;

    @XmlElement(required = true)
    private String toDate;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
