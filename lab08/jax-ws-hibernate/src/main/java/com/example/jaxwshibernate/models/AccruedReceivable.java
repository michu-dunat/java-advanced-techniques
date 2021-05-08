package com.example.jaxwshibernate.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class AccruedReceivable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate dueDate;
    private Float priceToPay;

    @ManyToOne
    @JoinColumn(name = "installation_id")
    private Installation installationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Float getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(Float priceToPay) {
        this.priceToPay = priceToPay;
    }

    public Installation getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Installation installationId) {
        this.installationId = installationId;
    }

    public AccruedReceivable() {
    }

    public AccruedReceivable(LocalDate dueDate, Float priceToPay, Installation installationId) {
        this.dueDate = dueDate;
        this.priceToPay = priceToPay;
        this.installationId = installationId;
    }
}
