package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoArtistUpdate {
    private Long id;
    private String name;
    private String biography;
    private String imageUrl;
}