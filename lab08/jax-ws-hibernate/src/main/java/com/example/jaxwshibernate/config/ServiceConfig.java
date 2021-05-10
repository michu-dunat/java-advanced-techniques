package com.example.jaxwshibernate.config;

import com.example.jaxwshibernate.services.ServiceServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class ServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    ServiceServiceImpl serviceService;

    @Bean
    public Endpoint serviceEndpoint() {
        Endpoint endpoint = new EndpointImpl(bus, serviceService);
        endpoint.publish("/serviceservice");
        return endpoint;
    }
}
