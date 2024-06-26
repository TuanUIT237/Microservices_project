package com.tuan.ebankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuan.ebankservice.entity.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {}
