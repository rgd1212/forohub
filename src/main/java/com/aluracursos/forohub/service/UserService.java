package com.aluracursos.forohub.service;

import com.alura.ForoHub_challenge_back_end.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails checkCredentials(String username, String rawPassword) {
        return userRepository.findByUsername(username);
    }
}
