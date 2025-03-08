package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPodcastInsert {

    private String title;

    private String author;

    private String description;

    private String publisher;

    private String language;

    private Boolean explicit;

    private String imageUrl;

    private List<Long> categoryIds;

}