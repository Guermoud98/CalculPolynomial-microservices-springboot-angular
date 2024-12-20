package com.emsi.servicefactorisation.repository;

import com.emsi.servicefactorisation.entity.Polynomial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolynomialRepository extends JpaRepository<Polynomial, Long> {
}
