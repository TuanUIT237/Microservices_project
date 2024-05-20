package com.tuan.ebankservice.entity;

import com.tuan.ebankservice.util.LoanStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    Integer installmentCount;
    BigDecimal principalLoanAmount;
    BigDecimal monthlyInstallmentAmount;
    BigDecimal interestToBePaid;
    BigDecimal principalToBePaid;
    BigDecimal remainingPrincipal;
    LocalDate dueDate;
    String status;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name="loan_id")
    Set<LoanPayment> loanPayments;
}
