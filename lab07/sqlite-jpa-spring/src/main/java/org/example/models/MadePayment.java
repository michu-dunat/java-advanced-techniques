package org.example.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MadePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate paymentDay;
    private Float paymentAmount;

    @ManyToOne
    @JoinColumn(name = "installation_id")
    private Installation installationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public Float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Installation getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Installation installationId) {
        this.installationId = installationId;
    }

    public MadePayment() {
    }

    public MadePayment(LocalDate paymentDay, Float paymentAmount, Installation installationId) {
        this.paymentDay = paymentDay;
        this.paymentAmount = paymentAmount;
        this.installationId = installationId;
    }
}
