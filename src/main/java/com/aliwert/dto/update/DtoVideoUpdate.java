package com.aliwert.dto.update;

import java.util.List;

import lombok.Data;


@Data
public class DtoVideoUpdate {
    private String title;

    private String description;

    private String videoType;

    private Long artistId;

    private Long albumId;

    private List<Long> genreIds;

}
