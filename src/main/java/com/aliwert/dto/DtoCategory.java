package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoCategory extends DtoBaseEntity {
    private String name;
    private String description;
    private String imageUrl;
    private List<Long> trackIds;
}