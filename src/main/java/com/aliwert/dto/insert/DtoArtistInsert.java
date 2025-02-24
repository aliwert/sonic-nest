package com.aliwert.dto.insert;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoArtistInsert {
    private String name;
    private String biography;
    private String imageUrl;
}