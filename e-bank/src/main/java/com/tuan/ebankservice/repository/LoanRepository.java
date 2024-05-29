package com.tuan.ebankservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuan.ebankservice.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByDueDateBefore(LocalDate now);
}
