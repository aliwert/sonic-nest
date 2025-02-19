package com.aliwert.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.DtoUser;
import com.aliwert.model.User;
import com.aliwert.repository.UserRepository;
import com.aliwert.service.IAuthenticationService;


@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User createUser(AuthRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        user.setCreatedTime(new Date());
        return user;
    }


    @Override
    public DtoUser register(AuthRequest req) {
        DtoUser user = new DtoUser();
        User savedUser =  userRepository.save(createUser(req)); 

        BeanUtils.copyProperties(savedUser, user);
       
        return user;


    }

    
}
