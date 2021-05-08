package com.example.jaxwshibernate.dto;

import com.example.jaxwshibernate.models.Installation;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(name = "ClientsResponse")
public class InstallationsResponse {

    private ArrayList<Installation> userInstallations;

    public ArrayList<Installation> getUserInstallations() {
        return userInstallations;
    }

    public void setUserInstallations(ArrayList<Installation> userInstallations) {
        this.userInstallations = userInstallations;
    }
}
