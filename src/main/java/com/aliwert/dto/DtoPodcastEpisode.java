package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoPodcastEpisode extends DtoBaseEntity {
    private String title;
    private String description;
    private Integer duration;
    private String audioUrl;
    private LocalDateTime releaseDate;
    private String imageUrl;
    private Integer episodeNumber;
    private Boolean explicit;
}