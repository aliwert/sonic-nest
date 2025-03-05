package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class DtoTrack extends DtoBaseEntity {

    private String title;

    private Duration duration;

    private String audioUrl;

    private DtoAlbum album;

    private List<DtoGenre> genres;

    private List<DtoCategory> categories;
    
}