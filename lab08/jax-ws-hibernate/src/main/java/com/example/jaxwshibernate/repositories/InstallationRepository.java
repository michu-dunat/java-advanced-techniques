package com.example.jaxwshibernate.repositories;

import com.example.jaxwshibernate.models.Client;
import com.example.jaxwshibernate.models.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Integer> {

    public ArrayList<Installation> findInstallationsByClientId(Client clientId);

}
