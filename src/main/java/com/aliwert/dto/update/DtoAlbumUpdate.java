package com.aliwert.dto.update;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class DtoAlbumUpdate {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String imageUrl;
    private Long artistId;
    private List<Long> genreIds;
}