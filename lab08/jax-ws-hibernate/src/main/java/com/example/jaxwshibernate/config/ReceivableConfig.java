package com.example.jaxwshibernate.config;

import com.example.jaxwshibernate.services.ReceivableServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class ReceivableConfig {

    @Autowired
    private Bus bus;

    @Autowired
    ReceivableServiceImpl receivableService;

    @Bean
    public Endpoint receivableEndpoint() {
        Endpoint endpoint = new EndpointImpl(bus, receivableService);
        endpoint.publish("/receivableservice");
        return endpoint;
    }
}
