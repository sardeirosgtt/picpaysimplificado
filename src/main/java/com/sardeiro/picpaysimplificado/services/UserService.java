package com.sardeiro.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.domain.user.UserType;
import com.sardeiro.picpaysimplificado.dtos.UserDTO;
import com.sardeiro.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuario do tipo Logista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount)<0){
            throw new Exception("Saldo suficiente");
        }
    }

    public User findUserById(Long id) throws Exception{
        return this.repository.findById(id).orElseThrow(()-> new Exception("Usuario não encontrado"));
    }

    public void saveuser(User user){
        this.repository.save(user);
    }

    public User createUser(UserDTO user) {
        User newUser = new User(user);
        this.saveuser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
       return this.repository.findAll();
    }
    
}
