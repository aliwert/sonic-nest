package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoChapter extends DtoBaseEntity {
    private String title;

    private Integer duration;

    private Integer chapterNumber;

    private String audioUrl;

    private DtoAudiobook audiobook;
}