package com.aliwert.service;

import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.AuthResponse;
import com.aliwert.dto.DtoUser;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest req);

    public AuthResponse authenticate(AuthRequest req);

    
}
