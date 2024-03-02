package com.example.jwtauth.domain;

import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document("roles")
@Getter
@Setter
public class Role {

    @Id
    private Long id;

    @Size(max = 255)
    private String name;

    @DocumentReference(lazy = true, lookup = "{ 'roleId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<User> userId;

}
