package com.aliwert.service;

import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import java.util.List;

public interface ChapterService {
    List<DtoChapter> getAllChapters();
    DtoChapter getChapterById(Long id);
    DtoChapter createChapter(DtoChapterInsert dtoChapterInsert);
    DtoChapter updateChapter(Long id, DtoChapterUpdate dtoChapterUpdate);
    void deleteChapter(Long id);
}