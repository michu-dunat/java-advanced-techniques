package com.example.jaxwshibernate.dto;

import com.example.jaxwshibernate.models.Service;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(name = "ServicesResponse")
public class ServicesResponse {

    private ArrayList<Service> services;

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
