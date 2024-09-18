package com.sardeiro.picpaysimplificado.repositories;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.sardeiro.picpaysimplificado.domain.user.User;
import com.sardeiro.picpaysimplificado.domain.user.UserType;
import com.sardeiro.picpaysimplificado.dtos.UserDTO;
import com.sardeiro.picpaysimplificado.services.UserService;
import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("teste")
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Caso onde tenha o usuario no DB")
    void testFindByDocument1() {
        String document  = "99999999901";
        UserDTO data = new UserDTO("andre", "andre", document, new BigDecimal(10), "teste@gmail.com", "444", UserType.COMMON);
        this.createuser(data);

        Optional<User> result =  this.repository.findByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Caso onde Não tenha o usuario no DB")
    void testFindByDocument2() {
        String document  = "99999999901";

        Optional<User> result =  this.repository.findByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createuser(UserDTO data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}
