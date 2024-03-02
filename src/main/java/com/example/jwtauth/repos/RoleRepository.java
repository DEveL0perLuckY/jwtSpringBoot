package com.example.jwtauth.repos;

import com.example.jwtauth.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, Long> {
}
