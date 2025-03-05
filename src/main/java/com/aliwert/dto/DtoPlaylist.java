package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPlaylist extends DtoBaseEntity {
    private String name;
    private String description;
    private boolean isPublic;
    private DtoUser user;
    private List<DtoTrack> tracks;
}