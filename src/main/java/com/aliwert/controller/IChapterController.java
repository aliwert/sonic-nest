package com.aliwert.controller;

import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import java.util.List;

public interface IChapterController {
    RootEntity<List<DtoChapter>> getAllChapters();
    RootEntity<DtoChapter> getChapterById(Long id);
    RootEntity<DtoChapter> createChapter(DtoChapterInsert dtoChapterInsert);
    RootEntity<DtoChapter> updateChapter(Long id, DtoChapterUpdate dtoChapterUpdate);
    RootEntity<Void> deleteChapter(Long id);
}