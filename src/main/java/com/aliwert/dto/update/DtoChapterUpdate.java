package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;
import com.aliwert.dto.DtoAudiobook;

@Getter
@Setter
public class DtoChapterUpdate {
    private Long id;

    private String title;

    private Integer duration;

    private Integer chapterNumber;

    private String audioUrl;
    
    private DtoAudiobook audiobook;
}