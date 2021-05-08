package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ReceivableRequest;
import com.example.jaxwshibernate.dto.ReceivableResponse;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "ReceivableService")
public interface ReceivableService {

    public ReceivableResponse getAllUserReceivableBetweenDates(@XmlElement(required=true) ReceivableRequest request);

}
