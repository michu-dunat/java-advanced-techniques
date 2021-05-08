package com.example.jaxwshibernate.config;

import javax.xml.ws.Endpoint;

import com.example.jaxwshibernate.services.ClientServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientConfig {

    @Autowired
    private Bus bus;

    @Autowired
    ClientServiceImpl clientService;

    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(bus, clientService);
        endpoint.publish("/clientservice");
        return endpoint;
    }

}
