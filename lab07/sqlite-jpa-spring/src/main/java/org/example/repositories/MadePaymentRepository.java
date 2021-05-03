package org.example.repositories;

import org.example.models.MadePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MadePaymentRepository extends JpaRepository<MadePayment, Integer> {
}
