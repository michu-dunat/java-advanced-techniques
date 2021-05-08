package com.example.jaxwshibernate.dto;

import com.example.jaxwshibernate.models.Client;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(name = "ClientsResponse")
public class ClientsResponse {

    private ArrayList<Client> clients;

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
}
