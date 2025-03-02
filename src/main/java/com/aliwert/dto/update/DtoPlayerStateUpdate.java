package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPlayerStateUpdate {
    private Long id;
    private Long userId;
    private Long currentTrackId;
    private Integer progressMs;
    private Boolean isPlaying;
    private Boolean shuffleState;
    private String repeatState;
    private Integer volume;
}