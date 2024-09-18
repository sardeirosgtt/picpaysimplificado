package com.sardeiro.picpaysimplificado.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.domain.user.UserType;
import com.sardeiro.picpaysimplificado.dtos.TransactionDTO;
import com.sardeiro.picpaysimplificado.repositories.TransactionRepository;




public class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private NotificationService notificationService;

    @Mock
    AuthorizationService authorizationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("caso da transação que tem sucesso em todas as verificações")
    void testCreatetransactionCase1() throws Exception {

        User sender = new User(1L,"maria","souza","99999999901","teste@example.com","12345",new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L,"joao","souza","99999999902","joao@example.com","12345",new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createtransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));

        verify(userService, times(1)).saveuser(sender);

        receiver.setBalance(new BigDecimal(20));

        verify(userService, times(1)).saveuser(receiver);

        verify(notificationService, times(1)).sendNotifiation(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotifiation(receiver, "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("caso da transação que nao está autorizada")
    void testCreatetransactionCase2() throws Exception {

        User sender = new User(1L,"maria","souza","99999999901","teste@example.com","12345",new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L,"joao","souza","99999999902","joao@example.com","12345",new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(false);

        
        Exception trhron = Assertions.assertThrows(Exception.class,()->{
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionService.createtransaction(request);
        });

        Assertions.assertEquals("Transação nao autorizada", trhron.getMessage());

    }
    
}
