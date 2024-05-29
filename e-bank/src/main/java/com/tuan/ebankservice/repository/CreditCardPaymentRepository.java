package com.tuan.ebankservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tuan.ebankservice.entity.CreditCardPayment;

@Repository
public interface CreditCardPaymentRepository extends JpaRepository<CreditCardPayment, String> {
    @Query(
            value =
                    "SELECT * from credit_card_payment where credit_card_id=:credit_card_id and payment_date >= :start_date and payment_date<= :end_date",
            nativeQuery = true)
    List<CreditCardPayment> findAllByCreditCardIdAndPaymentDateBetween(
            @Param("credit_card_id") String creditCardId,
            @Param("start_date") LocalDateTime start,
            @Param("end_date") LocalDateTime end);

    @Query(value = "select credit_card_id from credit_card_payment where id=:id", nativeQuery = true)
    String findByCreditCardId(@Param("id") String id);
}
