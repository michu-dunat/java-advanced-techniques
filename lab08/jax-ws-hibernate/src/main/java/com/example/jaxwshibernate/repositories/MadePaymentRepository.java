package com.example.jaxwshibernate.repositories;

import com.example.jaxwshibernate.models.MadePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MadePaymentRepository extends JpaRepository<MadePayment, Integer> {
}
