package com.aliwert.mapper;

import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import com.aliwert.model.Album;
import com.aliwert.model.Category;
import com.aliwert.model.Genre;
import com.aliwert.model.Track;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrackMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "album", source = "album", qualifiedByName = "albumToSimpleDto")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "genresToSimpleDtos")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoriesToSimpleDtos")
    DtoTrack toDto(Track track);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Track toEntity(DtoTrackInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromDto(DtoTrackUpdate dto, @MappingTarget Track track);

    List<DtoTrack> toDtoList(List<Track> tracks);

    // simple mapping methods to avoid circular dependencies
    @Named("albumToSimpleDto")
    default com.aliwert.dto.DtoAlbum albumToSimpleDto(Album album) {
        if (album == null) {
            return null;
        }
        com.aliwert.dto.DtoAlbum dto = new com.aliwert.dto.DtoAlbum();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setImageUrl(album.getImageUrl());
        dto.setCreateTime(dateToSqlDate(album.getCreatedTime()));
        if (album.getArtist() != null) {
            dto.setArtistId(album.getArtist().getId());
        }
        return dto;
    }

    @Named("genresToSimpleDtos")
    default List<com.aliwert.dto.DtoGenre> genresToSimpleDtos(List<Genre> genres) {
        if (genres == null) {
            return java.util.Collections.emptyList();
        }
        return genres.stream()
                .map(this::genreToSimpleDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Named("categoriesToSimpleDtos")
    default List<com.aliwert.dto.DtoCategory> categoriesToSimpleDtos(List<Category> categories) {
        if (categories == null) {
            return java.util.Collections.emptyList();
        }
        return categories.stream()
                .map(this::categoryToSimpleDto)
                .collect(java.util.stream.Collectors.toList());
    }

    default com.aliwert.dto.DtoGenre genreToSimpleDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        com.aliwert.dto.DtoGenre dto = new com.aliwert.dto.DtoGenre();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        dto.setCreateTime(dateToSqlDate(genre.getCreatedTime()));
        // Don't include trackIds to avoid circular reference
        return dto;
    }

    default com.aliwert.dto.DtoCategory categoryToSimpleDto(Category category) {
        if (category == null) {
            return null;
        }
        com.aliwert.dto.DtoCategory dto = new com.aliwert.dto.DtoCategory();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setCreateTime(dateToSqlDate(category.getCreatedTime()));
        // don't include trackIds to avoid circular reference
        return dto;
    }
}
