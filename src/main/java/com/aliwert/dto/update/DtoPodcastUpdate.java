package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPodcastUpdate {

    private Long id;

    private String title;

    private String author;

    private String description;

    private String publisher;

    private String language;

    private Boolean explicit;

    private String imageUrl;

    private List<Long> categoryIds;

}