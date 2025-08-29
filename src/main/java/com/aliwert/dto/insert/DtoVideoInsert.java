package com.aliwert.dto.insert;

import java.util.List;

import lombok.Data;


@Data
public class DtoVideoInsert {
    private String title;

    private String description;

    private String videoType;

    private Long artistId;

    private Long albumId;
    
    private List<Long> genreIds;
}
