package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class DtoTrackInsert {

    private String title;

    private Duration duration;

    private String audioUrl;

    private Long albumId;

    private List<Long> genreIds;

    private List<Long> categoryIds;
    
}