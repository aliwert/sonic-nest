package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPodcast extends DtoBaseEntity {
    private String title;
    private String author;
    private String description;
    private String publisher;
    private String language;
    private Boolean explicit;
    private String imageUrl;
    private List<DtoPodcastEpisode> episodes;
    private List<DtoCategory> categories;
}