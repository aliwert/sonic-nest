package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends DtoBaseEntity {

    private String username;

    private String email;
    
}
