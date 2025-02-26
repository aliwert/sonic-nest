package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IChapterController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import com.aliwert.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterControllerImpl extends BaseController implements IChapterController {

    private final ChapterService chapterService;

    @GetMapping
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoChapter>> getAllChapters() {
        return ok(chapterService.getAllChapters());
    }

    @GetMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoChapter> getChapterById(@PathVariable Long id) {
        return ok(chapterService.getChapterById(id));
    }

    @PostMapping
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoChapter> createChapter(@RequestBody DtoChapterInsert dtoChapterInsert) {
        return ok(chapterService.createChapter(dtoChapterInsert));
    }

    @PutMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoChapter> updateChapter(@PathVariable Long id, @RequestBody DtoChapterUpdate dtoChapterUpdate) {
        return ok(chapterService.updateChapter(id, dtoChapterUpdate));
    }

    @DeleteMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return null;
    }
}