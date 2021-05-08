package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ClientRequest;
import com.example.jaxwshibernate.dto.ClientUpdateRequest;
import com.example.jaxwshibernate.dto.ClientsResponse;
import com.example.jaxwshibernate.models.Client;
import com.example.jaxwshibernate.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientsResponse getAllClients() {
        ArrayList<Client> clients = (ArrayList<Client>) clientRepository.findAll();
        ClientsResponse response = new ClientsResponse();
        response.setClients(clients);
        return response;
    }

    @Override
    public Integer addClient(ClientRequest request) {
        Client client = new Client(request.getFirstName(), request.getLastName());
        client = clientRepository.save(client);
        return client.getId();
    }

    @Override
    public void deleteClient(Integer clientId) {
        Client client = clientRepository.getOne(clientId);
        clientRepository.delete(client);
    }


}
