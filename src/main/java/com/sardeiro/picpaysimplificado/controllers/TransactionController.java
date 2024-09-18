package com.sardeiro.picpaysimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sardeiro.picpaysimplificado.domain.transaction.Transaction;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.dtos.TransactionDTO;
import com.sardeiro.picpaysimplificado.services.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction>createTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = this.service.createtransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
    
    
}
