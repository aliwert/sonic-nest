package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoEpisode extends DtoBaseEntity {
    private String title;

    private String description;

    private Integer duration;

    private String audioUrl;

    private LocalDateTime releaseDate;

    private String imageUrl;
    
    // private DtoShow show;
}