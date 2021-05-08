package com.example.jaxwshibernate.config;

import com.example.jaxwshibernate.services.InstallationServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class InstallationConfig {

    @Autowired
    private Bus bus;

    @Autowired
    InstallationServiceImpl installationService;

    @Bean
    public Endpoint installationEndpoint() {
        Endpoint endpoint = new EndpointImpl(bus, installationService);
        endpoint.publish("/installationservice");
        return endpoint;
    }
}
