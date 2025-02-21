package com.aliwert.controller;

import com.aliwert.dto.AuthRequest;
import com.aliwert.dto.AuthResponse;
import com.aliwert.dto.DtoUser;
import com.aliwert.dto.RefreshTokenReq;

public interface IAuthenticationController {
    public RootEntity<DtoUser> register(AuthRequest req);

    public RootEntity<AuthResponse> authenticate(AuthRequest req);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenReq refreshToken);

}
