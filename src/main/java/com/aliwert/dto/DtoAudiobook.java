package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoAudiobook extends DtoBaseEntity {
    private String title;
    private String author;
    private String narrator;
    private String description;
    private String publisher;
    private Integer duration;
    private String imageUrl;
    private List<Long> chapterIds;
}