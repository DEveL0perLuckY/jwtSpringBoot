package com.example.jwtauth.repos;

import com.example.jwtauth.domain.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TreeRepository extends MongoRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    List<Token> findByUser_IdAndLoggedOutIsFalse(Long userId);

}
