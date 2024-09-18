package com.sardeiro.picpaysimplificado.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sardeiro.picpaysimplificado.domain.transaction.Transaction;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.dtos.TransactionDTO;
import com.sardeiro.picpaysimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    AuthorizationService authorizationService;

    public Transaction createtransaction(TransactionDTO transaction) throws Exception{
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validadeTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transaction.value());

        if (!isAuthorized) {
            throw new Exception("Transação nao autorizada");
        }

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(transaction.value());
        transaction2.setSender(sender);
        transaction2.setReceiver(receiver);
        transaction2.setTimesTamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));

        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(transaction2);
        this.userService.saveuser(sender);
        this.userService.saveuser(receiver);

        this.notificationService.sendNotifiation(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotifiation(receiver, "Transação recebida com sucesso");

        return transaction2;

    }
}
