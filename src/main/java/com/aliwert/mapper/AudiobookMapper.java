package com.aliwert.mapper;

import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import com.aliwert.model.Audiobook;
import com.aliwert.model.Chapter;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AudiobookMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "chapterIds", source = "chapters", qualifiedByName = "chaptersToChapterIds")
    DtoAudiobook toDto(Audiobook audiobook);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    Audiobook toEntity(DtoAudiobookInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    void updateEntityFromDto(DtoAudiobookUpdate dto, @MappingTarget Audiobook audiobook);

    List<DtoAudiobook> toDtoList(List<Audiobook> audiobooks);

    @Named("chaptersToChapterIds")
    default List<Long> chaptersToChapterIds(List<Chapter> chapters) {
        if (chapters == null) {
            return Collections.emptyList();
        }
        return chapters.stream()
                .map(Chapter::getId)
                .collect(Collectors.toList());
    }
}
