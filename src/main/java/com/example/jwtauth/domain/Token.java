package com.example.jwtauth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//token
@Document("token")
@Getter
@Setter
public class Token {
    @Id
    private Long id;
    private boolean loggedOut;
    private String token;
    @DBRef
    private User user;
}