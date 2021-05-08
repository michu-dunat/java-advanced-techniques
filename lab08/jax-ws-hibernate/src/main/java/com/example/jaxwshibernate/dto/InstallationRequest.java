package com.example.jaxwshibernate.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "InstallationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallationRequest {

    @XmlElement(required = true)
    private String address;

    @XmlElement(required = true)
    private Integer routerId;

    @XmlElement(required = true)
    private Integer clientId;

    @XmlElement(required = true)
    private Integer serviceId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRouterId() {
        return routerId;
    }

    public void setRouterId(Integer routerId) {
        this.routerId = routerId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
