package com.aliwert.dto.update;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPlaylistUpdate {
    private Long id;
    private String name;
    private String description;
    private boolean isPublic;
    private Long userId;
    private List<Long> trackIds;
}