package com.aliwert.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;


@Data
public class DtoVideo {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer duration;
    private Long viewCount;
    private LocalDate uploadDate;
    private String videoType;
    private DtoArtist artist;
    private DtoAlbum album;
    private List<DtoGenre> genres;
}
