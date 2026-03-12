package com.ohhigordev.io.payment_gateway.services;

import com.ohhigordev.io.payment_gateway.domain.Transaction;
import com.ohhigordev.io.payment_gateway.domain.User;
import com.ohhigordev.io.payment_gateway.dto.TransactionDTO;
import com.ohhigordev.io.payment_gateway.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) throws Exception{
        User sender = userService.findUserById(transaction.senderId());
        User receiver = userService.findUserById(transaction.receiverId());

        // Valida se o pagador pode enviar o dinheiro
        userService.validateTransaction(sender, transaction.value());

        // Realiza o débito e o crédito
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        // Salvar os dados
        userService.saveUser(sender);
        userService.saveUser(receiver);

        // Criar e salvar uma transação
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        return repository.save(newTransaction);
    }

}
