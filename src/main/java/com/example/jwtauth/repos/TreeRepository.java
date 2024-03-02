package com.example.jwtauth.repos;

import com.example.jwtauth.domain.Tree;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TreeRepository extends MongoRepository<Tree, Long> {
    Optional<Tree> findByToken(String token);

    List<Tree> findByUser_IdAndLoggedOutIsFalse(Long userId);

}
