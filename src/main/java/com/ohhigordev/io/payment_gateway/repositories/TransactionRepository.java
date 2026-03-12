package com.ohhigordev.io.payment_gateway.repositories;

import com.ohhigordev.io.payment_gateway.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
