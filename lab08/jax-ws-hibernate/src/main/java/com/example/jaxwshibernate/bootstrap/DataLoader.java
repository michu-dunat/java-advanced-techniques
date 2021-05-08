package com.example.jaxwshibernate.bootstrap;

import com.example.jaxwshibernate.models.AccruedReceivable;
import com.example.jaxwshibernate.models.Client;
import com.example.jaxwshibernate.models.Installation;
import com.example.jaxwshibernate.models.Service;
import com.example.jaxwshibernate.repositories.AccruedReceivableRepository;
import com.example.jaxwshibernate.repositories.ClientRepository;
import com.example.jaxwshibernate.repositories.InstallationRepository;
import com.example.jaxwshibernate.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    InstallationRepository installationRepository;

    @Autowired
    AccruedReceivableRepository accruedReceivableRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Client client = new Client("Współczesny", "Filozof");
        clientRepository.save(client);
        Client client1 = new Client("Nike", "Miedziana");
        clientRepository.save(client1);
        Client client2 = new Client("Artur", "Pięćzłotych");
        clientRepository.save(client2);
        Client client3 = new Client("Paweł", "Swing");
        clientRepository.save(client3);

        Service service = new Service("Internet 150Mb/s", (float) 45.00);
        serviceRepository.save(service);
        Service service1 = new Service("Internet 300Mb/s", (float) 50.00);
        serviceRepository.save(service1);
        Service service2 = new Service("Światłowód 750Mb/s", (float) 55.00);
        serviceRepository.save(service2);
        Service service3 = new Service("Światłowód 1Gsb/s", (float) 60.00);
        serviceRepository.save(service3);

        Installation installation = new Installation("Tychy", 123, service2, client);
        installationRepository.save(installation);
        Installation installation1 = new Installation("Wrocław", 1234, service1, client);
        installationRepository.save(installation1);
        Installation installation2 = new Installation("Warszawa", 1235, service3, client1);
        installationRepository.save(installation2);

        AccruedReceivable accruedReceivable = new AccruedReceivable(LocalDate.of(2021, 5, 8),installation.getServiceId().getPrice(), installation);
        accruedReceivableRepository.save(accruedReceivable);
        AccruedReceivable accruedReceivable1 = new AccruedReceivable(LocalDate.of(2021, 5, 10),installation.getServiceId().getPrice(), installation);
        accruedReceivableRepository.save(accruedReceivable1);
        AccruedReceivable accruedReceivable2 = new AccruedReceivable(LocalDate.of(2021, 5, 6),installation1.getServiceId().getPrice(), installation1);
        accruedReceivableRepository.save(accruedReceivable2);
        AccruedReceivable accruedReceivable3 = new AccruedReceivable(LocalDate.of(2021, 5, 8),installation2.getServiceId().getPrice(), installation2);
        accruedReceivableRepository.save(accruedReceivable3);




    }
}
