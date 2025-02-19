package com.aliwert.controller;

import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.DtoUser;

public interface IAuthenticationController {
    public RootEntity<DtoUser> register(AuthRequest req);
    
}
