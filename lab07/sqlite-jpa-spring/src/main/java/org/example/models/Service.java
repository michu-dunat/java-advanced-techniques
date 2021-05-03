package org.example.models;

import javax.persistence.*;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String serviceType;
    private Float price;

    @OneToOne(mappedBy = "serviceId")
    private Installation installation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Service() {
    }

    public Service(String serviceType, Float price) {
        this.serviceType = serviceType;
        this.price = price;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
