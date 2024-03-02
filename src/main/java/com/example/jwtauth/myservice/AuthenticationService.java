package com.example.jwtauth.myservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtauth.domain.Role;
import com.example.jwtauth.domain.Tree;
import com.example.jwtauth.domain.User;
import com.example.jwtauth.model.ConstantsId;
import com.example.jwtauth.repos.RoleRepository;
import com.example.jwtauth.repos.TreeRepository;
import com.example.jwtauth.repos.UserRepository;
import java.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@SuppressWarnings("null")
public class AuthenticationService {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TreeRepository tokenRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request) {

        // check if user already exist. if exist than authenticate the user
        if (repository.findByUserName(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }
        Optional<Role> roleUser = roleRepository.findById(ConstantsId.ROLE_USER);

        Set<Role> roles = new HashSet<>();
        if (roleUser.isPresent()) {
            roles.add(roleUser.get());
        } else {
            Role admin = new Role();
            admin.setId(ConstantsId.ROLE_ADMIN);
            admin.setName("ROLE_ADMIN");

            Role user = new Role();
            user.setId(ConstantsId.ROLE_USER);
            user.setName("ROLE_USER");

            // Save all roles in the repository
            roleRepository.saveAll(Arrays.asList(admin, user));
            roles.add(admin);
            roles.add(user);
        }
        User user = new User();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoleId(roles);
        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = repository.findByUserName(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");

    }

    private void revokeAllTokenByUser(User user) {
        List<Tree> validTokens = tokenRepository.findByUser_IdAndLoggedOutIsFalse(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Tree token = new Tree();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
