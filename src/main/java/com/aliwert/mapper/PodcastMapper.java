package com.aliwert.mapper;

import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import com.aliwert.model.Podcast;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PodcastMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "episodes", ignore = true) // handle manually to avoid circular deps
    @Mapping(target = "categories", ignore = true) // handle manually to avoid circular deps
    DtoPodcast toDto(Podcast podcast);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Podcast toEntity(DtoPodcastInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromDto(DtoPodcastUpdate dto, @MappingTarget Podcast podcast);

    List<DtoPodcast> toDtoList(List<Podcast> podcasts);
}
