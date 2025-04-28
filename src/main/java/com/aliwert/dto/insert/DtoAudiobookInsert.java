package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAudiobookInsert {
    private String title;
    private String author;
    private String narrator;
    private String description;
    private String publisher;
    private Integer duration;
    private String imageUrl;
}