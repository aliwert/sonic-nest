package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMarket extends DtoBaseEntity {
    private String countryCode;
    private String countryName;
    private String currency;
}