package com.sardeiro.picpaysimplificado.dtos;

import java.math.BigDecimal;
import com.sardeiro.picpaysimplificado.domain.user.UserType;

public record UserDTO(String firstName,String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
    
}
