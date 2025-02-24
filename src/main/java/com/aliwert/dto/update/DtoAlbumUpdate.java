package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DtoAlbumUpdate {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String imageUrl;
    private Long artistId;
    private List<Long> genreIds;
}