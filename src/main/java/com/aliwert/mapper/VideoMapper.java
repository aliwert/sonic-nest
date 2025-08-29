package com.aliwert.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.aliwert.dto.DtoVideo;
import com.aliwert.dto.insert.DtoVideoInsert;
import com.aliwert.dto.update.DtoVideoUpdate;
import com.aliwert.model.Video;

@Mapper(componentModel = "spring", uses = {ArtistMapper.class, AlbumMapper.class, GenreMapper.class})
public interface VideoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "videoUrl", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    
    Video toEntity(DtoVideoInsert dtoVideoInsert);
    
    @Mapping(target = "artist", source = "artist")
    @Mapping(target = "album", source = "album")
    @Mapping(target = "genres", source = "genres")
    DtoVideo toDto(Video video);
    
    List<DtoVideo> toDtoList(List<Video> videos);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "videoUrl", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    void updateEntityFromDto(DtoVideoUpdate dtoVideoUpdate, @MappingTarget Video video);
}
