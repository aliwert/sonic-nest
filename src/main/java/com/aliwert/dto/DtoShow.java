package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoShow extends DtoBaseEntity {

    private String title;

    private String description;

    private String publisher;

    private String imageUrl;

    private List<DtoEpisode> episodes;
    
}