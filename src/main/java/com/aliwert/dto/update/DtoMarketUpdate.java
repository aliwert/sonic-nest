package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMarketUpdate {
    private Long id;
    private String countryCode;
    private String countryName;
    private String currency;
}