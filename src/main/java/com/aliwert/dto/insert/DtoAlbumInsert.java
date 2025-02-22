package com.aliwert.dto.insert;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class DtoAlbumInsert {
    private String title;
    private LocalDate releaseDate;
    private String imageUrl;
    private Long artistId;
    private List<Long> genreIds;
}