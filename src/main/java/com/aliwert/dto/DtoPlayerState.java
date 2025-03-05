package com.aliwert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPlayerState extends DtoBaseEntity {
    private DtoUser user;
    private DtoTrack currentTrack;
    private Integer progressMs;
    private Boolean isPlaying;
    private Boolean shuffleState;
    private String repeatState;
    private Integer volume;
}