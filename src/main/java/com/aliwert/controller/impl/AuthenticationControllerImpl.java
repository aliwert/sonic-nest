package com.aliwert.controller.impl;

import com.aliwert.dto.AuthResponse;
import com.aliwert.dto.RefreshTokenReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IAuthenticationController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.DtoUser;
import com.aliwert.service.IAuthenticationService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerImpl extends BaseController implements IAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;


    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest req) {
        return ok(authenticationService.register(req));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest req) {
        return ok(authenticationService.authenticate(req));
    }

    @PostMapping("/refreshtoken")
    @Override
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenReq req ){
        return ok(authenticationService.refreshToken(req));
    }


}
