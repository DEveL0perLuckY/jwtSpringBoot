package com.example.jwtauth.domain;

import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document("users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    private Long id;

    @Size(max = 50)
    private String userName;

    @Size(max = 50)
    private String email;

    @Size(max = 255)
    private String password;

    @DocumentReference(lazy = true)
    private Set<Role> roleId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleId.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;

    }

}
