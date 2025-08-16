package com.aliwert.mapper;

import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import com.aliwert.model.PodcastEpisode;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PodcastEpisodeMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    DtoPodcastEpisode toDto(PodcastEpisode podcastEpisode);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "podcast", ignore = true)
    PodcastEpisode toEntity(DtoPodcastEpisodeInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "podcast", ignore = true)
    void updateEntityFromDto(DtoPodcastEpisodeUpdate dto, @MappingTarget PodcastEpisode podcastEpisode);

    List<DtoPodcastEpisode> toDtoList(List<PodcastEpisode> podcastEpisodes);
}
