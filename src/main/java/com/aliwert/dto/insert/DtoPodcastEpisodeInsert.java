package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoPodcastEpisodeInsert {

    private String title;

    private String description;

    private Integer duration;

    private String audioUrl;

    private LocalDateTime releaseDate;

    private String imageUrl;

    private Integer episodeNumber;

    private Boolean explicit;

    private Long podcastId;

}