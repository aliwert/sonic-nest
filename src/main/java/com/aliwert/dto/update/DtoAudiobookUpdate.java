package com.aliwert.dto.update;

import lombok.Data;

@Data
public class DtoAudiobookUpdate {
    private Long id;
    private String title;
    private String author;
    private String narrator;
    private String description;
    private String publisher;
    private Integer duration;
    private String imageUrl;
}