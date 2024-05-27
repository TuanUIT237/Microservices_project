package com.tuan.ebankservice.repository;

import com.tuan.ebankservice.dto.loandto.LoanResponse;
import com.tuan.ebankservice.entity.Loan;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByDueDateBefore(LocalDate now);
}
