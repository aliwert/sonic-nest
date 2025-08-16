package com.aliwert.mapper;

import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import com.aliwert.model.Playlist;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "public", source = "public")
    DtoPlaylist toDto(Playlist playlist);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "public", source = "public")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    Playlist toEntity(DtoPlaylistInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "public", source = "public")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    void updateEntityFromDto(DtoPlaylistUpdate dto, @MappingTarget Playlist playlist);

    List<DtoPlaylist> toDtoList(List<Playlist> playlists);
}
