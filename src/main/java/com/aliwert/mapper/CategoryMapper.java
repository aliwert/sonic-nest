package com.aliwert.mapper;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import com.aliwert.model.Category;
import com.aliwert.model.Track;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "trackIds", source = "tracks", qualifiedByName = "tracksToTrackIds")
    DtoCategory toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    Category toEntity(DtoCategoryInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    void updateEntityFromDto(DtoCategoryUpdate dto, @MappingTarget Category category);

    List<DtoCategory> toDtoList(List<Category> categories);

    @Named("tracksToTrackIds")
    default List<Long> tracksToTrackIds(List<Track> tracks) {
        if (tracks == null) {
            return Collections.emptyList();
        }
        return tracks.stream()
                .map(Track::getId)
                .collect(Collectors.toList());
    }
}
