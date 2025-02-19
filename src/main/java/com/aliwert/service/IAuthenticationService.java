package com.aliwert.service;

import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.DtoUser;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest req);

    
}
