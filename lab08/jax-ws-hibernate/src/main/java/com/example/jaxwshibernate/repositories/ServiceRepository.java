package com.example.jaxwshibernate.repositories;

import com.example.jaxwshibernate.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
