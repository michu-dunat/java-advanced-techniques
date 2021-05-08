package com.example.jaxwshibernate.repositories;

import com.example.jaxwshibernate.models.AccruedReceivable;
import com.example.jaxwshibernate.models.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AccruedReceivableRepository extends JpaRepository<AccruedReceivable, Integer> {

    public ArrayList<AccruedReceivable> findAccruedReceivablesByInstallationId(Installation installationId);

}
