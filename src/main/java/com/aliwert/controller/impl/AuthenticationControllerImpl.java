package com.aliwert.controller.impl;

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

import jakarta.validation.Valid;

@RestController
public class AuthenticationControllerImpl extends BaseController implements IAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;


    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest req) {
        return ok(authenticationService.register(req));
    }

    
}
