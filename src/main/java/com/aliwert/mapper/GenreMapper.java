package com.aliwert.mapper;

import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import com.aliwert.model.Genre;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    DtoGenre toDto(Genre genre);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    Genre toEntity(DtoGenreInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    void updateEntityFromDto(DtoGenreUpdate dto, @MappingTarget Genre genre);

    List<DtoGenre> toDtoList(List<Genre> genres);
}
