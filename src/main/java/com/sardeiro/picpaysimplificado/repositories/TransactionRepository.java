package com.sardeiro.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sardeiro.picpaysimplificado.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    
} 
