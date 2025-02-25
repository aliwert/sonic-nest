package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCategoryUpdate {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}