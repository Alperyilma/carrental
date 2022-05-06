package com.lec.carrental.service;

import com.lec.carrental.domain.User;
import com.lec.carrental.exception.BadRequestException;
import com.lec.carrental.repository.RoleRepository;
import com.lec.carrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) throws BadRequestException {

    }



}
