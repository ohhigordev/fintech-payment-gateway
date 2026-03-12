package com.ohhigordev.io.payment_gateway.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne // Um usuário pode ter várias transações com o mesmo remetente;
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne // Um usuário pode ter várias transações com o mesmo destinatário;
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime timestamp;

}
