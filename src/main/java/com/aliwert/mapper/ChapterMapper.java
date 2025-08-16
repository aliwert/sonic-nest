package com.aliwert.mapper;

import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import com.aliwert.model.Chapter;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "audiobook", ignore = true) // avoid circular reference
    DtoChapter toDto(Chapter chapter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    Chapter toEntity(DtoChapterInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    void updateEntityFromDto(DtoChapterUpdate dto, @MappingTarget Chapter chapter);

    List<DtoChapter> toDtoList(List<Chapter> chapters);
}
