package com.ohhigordev.io.payment_gateway.controllers;

import com.ohhigordev.io.payment_gateway.domain.Transaction;
import com.ohhigordev.io.payment_gateway.dto.TransactionDTO;
import com.ohhigordev.io.payment_gateway.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction (@RequestBody TransactionDTO transaction) throws Exception{
          Transaction newTransaction = transactionService.createTransaction(transaction);
          return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
