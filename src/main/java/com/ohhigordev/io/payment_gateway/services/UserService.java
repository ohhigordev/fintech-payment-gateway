package com.ohhigordev.io.payment_gateway.services;

import com.ohhigordev.io.payment_gateway.domain.User;
import com.ohhigordev.io.payment_gateway.domain.UserType;
import com.ohhigordev.io.payment_gateway.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Lojistas não podem realizar transferências.");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente.");
        }
    }

    public User findUserById(Long id) throws Exception{
        return repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void saveUser(User user){
        repository.save(user);
    }

}
