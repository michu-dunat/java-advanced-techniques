package org.example.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String address;
    private Integer routerId;

    @OneToOne
    @JoinColumn(name = "service_id")
    private Service serviceId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clientId;

    @OneToMany(mappedBy = "installationId")
    private List<AccruedReceivable> accruedReceivable;

    @OneToMany(mappedBy = "installationId")
    private List<MadePayment> madePayment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRouterId() {
        return routerId;
    }

    public void setRouterId(Integer routerId) {
        this.routerId = routerId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Installation() {
    }

    public Installation(String address, Integer routerId, Service serviceId, Client clientId) {
        this.address = address;
        this.routerId = routerId;
        this.serviceId = serviceId;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
