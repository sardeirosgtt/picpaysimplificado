package com.sardeiro.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sardeiro.picpaysimplificado.domain.user.User;

@Service
public class AuthorizationService {
    
    @Autowired
    private RestTemplate restTemplate;

        public boolean authorizeTransaction(User sender, BigDecimal value){
       ResponseEntity<Map> response  = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize",Map.class);

       if (response.getStatusCode()== HttpStatus.OK) {
        String message = (String) response.getBody().get("status");
        return "success".equalsIgnoreCase(message);
       }else return false;
    }
    
}
