package com.aliwert.mapper;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import com.aliwert.model.Episode;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EpisodeMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "show", ignore = true) // avoid circular reference
    DtoEpisode toDto(Episode episode);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "show", ignore = true)
    Episode toEntity(DtoEpisodeInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "show", ignore = true)
    void updateEntityFromDto(DtoEpisodeUpdate dto, @MappingTarget Episode episode);

    List<DtoEpisode> toDtoList(List<Episode> episodes);
}
