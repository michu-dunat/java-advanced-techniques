package org.example.repositories;

import org.example.models.AccruedReceivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccruedReceivableRepository extends JpaRepository<AccruedReceivable, Integer> {
}
