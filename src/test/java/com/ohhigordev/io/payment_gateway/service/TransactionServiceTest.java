package com.ohhigordev.io.payment_gateway.service;

import com.ohhigordev.io.payment_gateway.domain.User;
import com.ohhigordev.io.payment_gateway.domain.UserType;
import com.ohhigordev.io.payment_gateway.dto.TransactionDTO;
import com.ohhigordev.io.payment_gateway.repositories.TransactionRepository;
import com.ohhigordev.io.payment_gateway.services.TransactionService;
import com.ohhigordev.io.payment_gateway.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception{
        // Arrange (Configuração)
        User sender = new User(1L, "Higor", "Silva", "123456", "higor@test.com", "123", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "Logista", "Teste", "654321", "loja@test.com", "321", new BigDecimal(50), UserType.MERCHANT);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        //Act (Ação)
        transactionService.createTransaction(request);

        //Assert (Verificação)
        verify(transactionRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is invalid")
    void createTransactionCase2() throws Exception{
        // Arrange
        User sender = new User(1L, "Higor", "Silva", "123456", "higor@test.com", "123", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "Logista", "Teste", "654321", "loja@test.com", "321", new BigDecimal(50), UserType.MERCHANT);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        // Forçando o service de usuário a lançar um erro de saldo insuficiente
        doThrow(new Exception("Saldo insuficiente")).when(userService).validateTransaction(any(), any());

        TransactionDTO request = new TransactionDTO(new BigDecimal(100), 1L, 2L);

        // Act & Assert
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Saldo insuficiente", thrown.getMessage());
    }

}
