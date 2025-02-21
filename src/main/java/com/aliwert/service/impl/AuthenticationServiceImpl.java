package com.aliwert.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.aliwert.dto.AuthResponse;
import com.aliwert.dto.RefreshTokenReq;
import com.aliwert.exception.BaseException;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.jwt.JwtService;
import com.aliwert.model.RefreshToken;
import com.aliwert.repository.IRefreshTokenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

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

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000*60*60*24*7));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }


    @Override
    public AuthResponse authenticate(AuthRequest req) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
            authenticationProvider.authenticate(authenticationToken);

           Optional<User> opt = userRepository.findByUsername(req.getUsername());
            String token = jwtService.generateToken(opt.get());
            RefreshToken savedRefreshToken =refreshTokenRepository.save(createRefreshToken(opt.get()));
            return new AuthResponse(token, savedRefreshToken.getRefreshToken());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INCORRECT, e.getMessage()));
        }

    }


    public boolean isTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenReq req) {
        Optional<RefreshToken> opt = refreshTokenRepository.findByRefreshToken(req.getRefreshToken());
        if(opt.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, req.getRefreshToken()));
        }
        if (!isTokenExpired(opt.get().getExpiredDate())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_EXPIRED, req.getRefreshToken()));
        }

        User user = opt.get().getUser();
        String generatedToken = jwtService.generateToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(generatedToken, savedRefreshToken.getRefreshToken());
    }
}
