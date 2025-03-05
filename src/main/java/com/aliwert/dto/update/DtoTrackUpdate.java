package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class DtoTrackUpdate {

    private Long id;

    private String title;

    private Duration duration;

    private String audioUrl;

    private Long albumId;

    private List<Long> genreIds;

    private List<Long> categoryIds;
    
}