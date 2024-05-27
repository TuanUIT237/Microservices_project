package com.tuan.ebankservice.repository;

import com.tuan.ebankservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByAccountNumberfromAndTransferDateBetween (String id, LocalDateTime startDate, LocalDateTime endDate);
}
