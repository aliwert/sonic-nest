package com.aliwert.service.impl;

import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Chapter;
import com.aliwert.model.Audiobook;
import com.aliwert.repository.ChapterRepository;
import com.aliwert.repository.AudiobookRepository;
import com.aliwert.service.ChapterService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final AudiobookRepository audiobookRepository;
    private final AudiobookServiceImpl audiobookService;

    @Override
    public List<DtoChapter> getAllChapters() {
        return chapterRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoChapter getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Chapter").prepareErrorMessage()));
        return convertToDto(chapter);
    }

    @Override
    public DtoChapter createChapter(DtoChapterInsert dtoChapterInsert) {
        Chapter chapter = new Chapter();
        updateChapterFromDto(chapter, dtoChapterInsert);
        return convertToDto(chapterRepository.save(chapter));
    }

    @Override
    public DtoChapter updateChapter(Long id, DtoChapterUpdate dtoChapterUpdate) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Chapter").prepareErrorMessage()));
        updateChapterFromDto(chapter, dtoChapterUpdate);
        return convertToDto(chapterRepository.save(chapter));
    }

    @Override
    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }

    private DtoChapter convertToDto(Chapter chapter) {
        DtoChapter dto = new DtoChapter();
        dto.setId(chapter.getId());
        dto.setTitle(chapter.getTitle());
        dto.setDuration(chapter.getDuration());
        dto.setChapterNumber(chapter.getChapterNumber());
        dto.setAudioUrl(chapter.getAudioUrl());

        // Handle creation time safely
        if (chapter.getCreatedTime() != null) {
            if (chapter.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) chapter.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(chapter.getCreatedTime().getTime()));
            }
        }

        // Handle audiobook reference safely
        if (chapter.getAudiobook() != null) {
            dto.setAudiobook(audiobookService.convertToDto(chapter.getAudiobook()));
        }

        return dto;
    }

    private void updateChapterFromDto(Chapter chapter, Object dto) {
        if (dto instanceof DtoChapterInsert insert) {
            updateChapterFields(chapter, insert.getTitle(), insert.getDuration(),
                    insert.getChapterNumber(), insert.getAudioUrl(), insert.getAudiobookId());
        } else if (dto instanceof DtoChapterUpdate update) {
            updateChapterFields(chapter, update.getTitle(), update.getDuration(),
                    update.getChapterNumber(), update.getAudioUrl(),
                    update.getAudiobook() != null ? update.getAudiobook().getId() : null);
        }
    }

    private void updateChapterFields(Chapter chapter, String title, Integer duration,
                                     Integer chapterNumber, String audioUrl, Long audiobookId) {
        chapter.setTitle(title);
        chapter.setDuration(duration);
        chapter.setChapterNumber(chapterNumber);
        chapter.setAudioUrl(audioUrl);

        // Handle audiobook reference safely
        if (audiobookId != null) {
            Audiobook audiobook = audiobookRepository.findById(audiobookId)
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
            chapter.setAudiobook(audiobook);
        }
    }
}