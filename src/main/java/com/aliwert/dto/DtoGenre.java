package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGenre extends DtoBaseEntity {
    private String name;
    private String description;
}