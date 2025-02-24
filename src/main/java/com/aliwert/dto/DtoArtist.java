package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoArtist extends DtoBaseEntity {
    private String name;
    private String biography;
    private String imageUrl;
    private List<Long> albumIds;
}