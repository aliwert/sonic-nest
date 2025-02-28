package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoEpisodeUpdate {

    private Long id;

    private String title;

    private String description;

    private Integer duration;

    private String audioUrl;

    private LocalDateTime releaseDate;

    private String imageUrl;

    private Long showId;
}