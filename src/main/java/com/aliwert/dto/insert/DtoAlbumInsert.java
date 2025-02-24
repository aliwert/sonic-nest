package com.aliwert.dto.insert;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DtoAlbumInsert {
    private String title;
    private LocalDate releaseDate;
    private String imageUrl;
    private Long artistId;
    private List<Long> genreIds;
}