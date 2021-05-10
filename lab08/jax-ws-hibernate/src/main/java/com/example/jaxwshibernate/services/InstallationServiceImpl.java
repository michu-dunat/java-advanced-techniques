package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.InstallationRequest;
import com.example.jaxwshibernate.dto.InstallationsResponse;
import com.example.jaxwshibernate.models.Client;
import com.example.jaxwshibernate.models.Installation;
import com.example.jaxwshibernate.repositories.ClientRepository;
import com.example.jaxwshibernate.repositories.InstallationRepository;
import com.example.jaxwshibernate.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InstallationServiceImpl implements InstallationService {

    @Autowired
    InstallationRepository installationRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @Override
    public InstallationsResponse getAllUserInstallations(Integer userId) {
        Client client = clientRepository.getOne(userId);
        ArrayList<Installation> installations = installationRepository.findInstallationsByClientId(client);
        InstallationsResponse response = new InstallationsResponse();
        response.setUserInstallations(installations);
        return response;
    }

    @Override
    public Integer addInstallation(InstallationRequest request) {
        com.example.jaxwshibernate.models.Service service = serviceRepository.getOne(request.getServiceId());
        Client client = clientRepository.getOne(request.getClientId());
        Installation installation = new Installation(request.getAddress(), request.getRouterId(), service, client);
        installation = installationRepository.save(installation);
        return installation.getId();
    }

    @Override
    public void deleteInstallation(Integer installationId) {
        Installation installation = installationRepository.getOne(installationId);
        installationRepository.delete(installation);
    }
}
