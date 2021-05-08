package com.example.jaxwshibernate.services;

import com.example.jaxwshibernate.dto.ReceivableRequest;
import com.example.jaxwshibernate.dto.ReceivableResponse;
import com.example.jaxwshibernate.models.AccruedReceivable;
import com.example.jaxwshibernate.models.Client;
import com.example.jaxwshibernate.models.Installation;
import com.example.jaxwshibernate.repositories.AccruedReceivableRepository;
import com.example.jaxwshibernate.repositories.ClientRepository;
import com.example.jaxwshibernate.repositories.InstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ReceivableServiceImpl implements ReceivableService{

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    InstallationRepository installationRepository;

    @Autowired
    AccruedReceivableRepository accruedReceivableRepository;

    @Override
    public ReceivableResponse getAllUserReceivableBetweenDates(ReceivableRequest request) {
        Client client = clientRepository.getOne(request.getClientId());
        ArrayList<Installation> userInstallations = installationRepository.findInstallationsByClientId(client);
        ArrayList<AccruedReceivable> userReceivables = new ArrayList<>();
        for (Installation installation : userInstallations
             ) {
            userReceivables.addAll(accruedReceivableRepository.findAccruedReceivablesByInstallationId(installation));
        }
        ArrayList<AccruedReceivable> toBeRemoved = new ArrayList<>();
        for (AccruedReceivable accruedReceivable : userReceivables
             ) {
            LocalDate fromDate = LocalDate.parse(request.getFromDate());
            LocalDate toDate = LocalDate.parse(request.getToDate());
            if (accruedReceivable.getDueDate().isBefore(fromDate) || accruedReceivable.getDueDate().isAfter(toDate))
                toBeRemoved.add(accruedReceivable);
        }
        userReceivables.removeAll(toBeRemoved);
        ReceivableResponse response = new ReceivableResponse();
        response.setReceivables(userReceivables);
        return response;
    }
}
