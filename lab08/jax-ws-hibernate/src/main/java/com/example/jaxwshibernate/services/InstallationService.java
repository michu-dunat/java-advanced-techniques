package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.InstallationRequest;
import com.example.jaxwshibernate.dto.InstallationsResponse;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "InstallationService")
public interface InstallationService {

    public InstallationsResponse getAllUserInstallations(@XmlElement(required=true) Integer userId);

    public Integer addInstallation(@XmlElement(required=true) InstallationRequest request);

    public void deleteInstallation(@XmlElement(required=true) Integer installationId);
}
