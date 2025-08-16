package com.aliwert.mapper;

import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import com.aliwert.model.Album;
import com.aliwert.model.Artist;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArtistMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "albumIds", source = "albums", qualifiedByName = "albumsToAlbumIds")
    DtoArtist toDto(Artist artist);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "albums", ignore = true)
    Artist toEntity(DtoArtistInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "albums", ignore = true)
    void updateEntityFromDto(DtoArtistUpdate dto, @MappingTarget Artist artist);

    List<DtoArtist> toDtoList(List<Artist> artists);

    @Named("albumsToAlbumIds")
    default List<Long> albumsToAlbumIds(List<Album> albums) {
        if (albums == null) {
            return Collections.emptyList();
        }
        return albums.stream()
                .map(Album::getId)
                .collect(Collectors.toList());
    }
}
