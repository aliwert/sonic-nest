package com.aliwert.mapper;

import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import com.aliwert.model.Show;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShowMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "episodes", ignore = true) // handle manually to avoid circular dependency
    DtoShow toDto(Show show);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    Show toEntity(DtoShowInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    void updateEntityFromDto(DtoShowUpdate dto, @MappingTarget Show show);

    List<DtoShow> toDtoList(List<Show> shows);
}
