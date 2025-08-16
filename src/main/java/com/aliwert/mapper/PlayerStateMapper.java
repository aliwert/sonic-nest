package com.aliwert.mapper;

import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import com.aliwert.model.PlayerState;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerStateMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "user", ignore = true) // Handle manually to avoid circular deps
    @Mapping(target = "currentTrack", ignore = true) // Handle manually to avoid circular deps
    DtoPlayerState toDto(PlayerState playerState);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "user", ignore = true) // Handle manually in service
    @Mapping(target = "currentTrack", ignore = true) // Handle manually in service
    PlayerState toEntity(DtoPlayerStateInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "user", ignore = true) // Handle manually in service
    @Mapping(target = "currentTrack", ignore = true) // Handle manually in service
    void updateEntityFromDto(DtoPlayerStateUpdate dto, @MappingTarget PlayerState playerState);

    List<DtoPlayerState> toDtoList(List<PlayerState> playerStates);
}
