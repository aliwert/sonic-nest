package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import com.aliwert.dto.DtoAudiobook;

@Getter
@Setter
public class DtoChapterInsert {
    private String title;

    private Integer duration;

    private Integer chapterNumber;

    private String audioUrl;
    
    private DtoAudiobook audiobook;
}