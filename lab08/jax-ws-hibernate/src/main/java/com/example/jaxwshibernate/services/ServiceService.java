package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ServicesResponse;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "ServiceService")
public interface ServiceService {

    public ServicesResponse getAllServices();

    public Integer addService(@XmlElement(required=true) String serviceType, @XmlElement(required=true) Float price);

    public void deleteService(@XmlElement(required=true) Integer serviceId);
}
