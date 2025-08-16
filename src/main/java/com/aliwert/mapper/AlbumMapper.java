package com.aliwert.mapper;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;
import com.aliwert.model.Album;
import com.aliwert.model.Artist;
import com.aliwert.model.Genre;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "artistId", source = "artist.id")
    @Mapping(target = "genreIds", source = "genres", qualifiedByName = "genresToGenreIds")
    DtoAlbum toDto(Album album);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Album toEntity(DtoAlbumInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "genres", ignore = true)
    void updateEntityFromDto(DtoAlbumUpdate dto, @MappingTarget Album album);

    List<DtoAlbum> toDtoList(List<Album> albums);

    @Named("genresToGenreIds")
    default List<Long> genresToGenreIds(List<Genre> genres) {
        if (genres == null) {
            return Collections.emptyList();
        }
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
    }

    // helper method to map artist ID for entities with artist relationships
    @Named("mapArtistId")
    default Long mapArtistId(Artist artist) {
        return artist != null ? artist.getId() : null;
    }
}
