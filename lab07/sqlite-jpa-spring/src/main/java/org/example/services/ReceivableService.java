package org.example.services;

import org.example.models.AccruedReceivable;
import org.example.models.Client;
import org.example.models.Installation;
import org.example.models.MadePayment;
import org.example.repositories.AccruedReceivableRepository;
import org.example.repositories.InstallationRepository;
import org.example.repositories.MadePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ReceivableService {

    @Autowired
    InstallationRepository installationRepository;
    @Autowired
    AccruedReceivableRepository accruedReceivableRepository;
    @Autowired
    MadePaymentRepository madePaymentRepository;

    public String charge(LocalDate date) {
        if (date.getDayOfMonth() == 10) {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Installation> installations = new ArrayList<>(installationRepository.findAll());
            for (Installation installation : installations
                 ) {
                LocalDate dueDate = date.plusMonths(1);
                AccruedReceivable accruedReceivable = new AccruedReceivable(dueDate,
                        installation.getServiceId().getPrice(),
                        installation);
                accruedReceivableRepository.save(accruedReceivable);
                stringBuilder.append(
                        installation.getClientId().getFirstName() +
                        " " +
                        installation.getClientId().getLastName() +
                        " has to pay " +
                        installation.getServiceId().getPrice() +
                        " for " +
                        installation.getServiceId().getServiceType() +
                        " due to " +
                        dueDate.toString() + "\n");

            }
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    public String checkPayments(LocalDate date) {
        if (date.getDayOfMonth() == 10) {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<AccruedReceivable> accruedReceivables = new ArrayList<>(accruedReceivableRepository.findAll());
            ArrayList<MadePayment> madePayments = new ArrayList<>(madePaymentRepository.findAll());
            for (AccruedReceivable accruedReceivable : accruedReceivables
            ) {
                float receivableSum = 0;
                float paymentSum = 0;
                Integer installationId = accruedReceivable.getInstallationId().getId();

                for (AccruedReceivable receivable : accruedReceivables
                     ) {
                    if (receivable.getInstallationId().getId().equals(installationId))
                        receivableSum += receivable.getPriceToPay();
                }

                for (MadePayment payment : madePayments
                     ) {
                    if (payment.getInstallationId().getId().equals(installationId))
                        paymentSum += payment.getPaymentAmount();
                }

                Client client = accruedReceivable.getInstallationId().getClientId();

                if (paymentSum < receivableSum) {
                    stringBuilder.append(client.getFirstName() + " " + client.getLastName() + " owes "
                            + (receivableSum - paymentSum) + " for " + accruedReceivable.getInstallationId().getServiceId().getServiceType()
                            + " at " + accruedReceivable.getInstallationId().getAddress() + "\n");
                }
            }
            return stringBuilder.toString();
        } else {
            return "";
        }
    }


}
