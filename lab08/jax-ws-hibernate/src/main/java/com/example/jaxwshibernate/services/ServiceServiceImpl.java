package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ServicesResponse;
import com.example.jaxwshibernate.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    ServiceRepository serviceRepository;


    @Override
    public ServicesResponse getAllServices() {
        ArrayList<com.example.jaxwshibernate.models.Service> services = (ArrayList<com.example.jaxwshibernate.models.Service>) serviceRepository.findAll();
        ServicesResponse servicesResponse = new ServicesResponse();
        servicesResponse.setServices(services);
        return servicesResponse;
    }

    @Override
    public Integer addService(String serviceType, Float price) {
        com.example.jaxwshibernate.models.Service service = new com.example.jaxwshibernate.models.Service(serviceType, price);
        service = serviceRepository.save(service);
        return service.getId();
    }

    @Override
    public void deleteService(Integer serviceId) {
        com.example.jaxwshibernate.models.Service service = serviceRepository.getOne(serviceId);
        serviceRepository.delete(service);
    }
}
