package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPlayerStateInsert {
    private Long userId;
    private Long currentTrackId;
    private Integer progressMs;
    private Boolean isPlaying;
    private Boolean shuffleState;
    private String repeatState;
    private Integer volume;
}