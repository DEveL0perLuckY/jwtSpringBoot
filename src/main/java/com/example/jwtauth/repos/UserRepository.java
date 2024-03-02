package com.example.jwtauth.repos;

import com.example.jwtauth.domain.Role;
import com.example.jwtauth.domain.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findFirstByRoleId(Role role);

    List<User> findAllByRoleId(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

}
