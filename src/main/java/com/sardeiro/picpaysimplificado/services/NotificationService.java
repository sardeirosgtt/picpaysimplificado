package com.sardeiro.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.dtos.NotificationDTO;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotifiation(User user, String message) throws Exception{
        String email = user.getEmail();

        NotificationDTO notificationRequest = new NotificationDTO(email, email);

    //    ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

    //    if (!(notificationResponse.getStatusCode()==HttpStatus.OK)) {
    //         System.out.println("Erro ao enviar notificação");
    //         throw new Exception("Servico de notificação está fora do ar");
    //    }

        System.out.println("Notificação enviada para o usuario ");
    }
    
}
