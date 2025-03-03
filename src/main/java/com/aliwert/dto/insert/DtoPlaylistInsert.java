package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtoPlaylistInsert {
    private String name;
    private String description;
    private boolean isPublic;
    private Long userId;
    private List<Long> trackIds;
}