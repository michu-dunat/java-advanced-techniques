package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ClientRequest;
import com.example.jaxwshibernate.dto.ClientsResponse;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "ClientService")
public interface ClientService {

    public ClientsResponse getAllClients();

    public Integer addClient(@XmlElement(required=true) ClientRequest request);

}
