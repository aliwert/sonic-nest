package com.aliwert.service.impl;

import com.aliwert.dto.DtoChapter;
import com.aliwert.dto.insert.DtoChapterInsert;
import com.aliwert.dto.update.DtoChapterUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.ChapterMapper;
import com.aliwert.model.Chapter;
import com.aliwert.model.Audiobook;
import com.aliwert.repository.ChapterRepository;
import com.aliwert.repository.AudiobookRepository;
import com.aliwert.service.ChapterService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final AudiobookRepository audiobookRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public List<DtoChapter> getAllChapters() {
        return chapterMapper.toDtoList(chapterRepository.findAll());
    }

    @Override
    public DtoChapter getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Chapter").prepareErrorMessage()));
        return chapterMapper.toDto(chapter);
    }

    @Override
    public DtoChapter createChapter(DtoChapterInsert dtoChapterInsert) {
        Chapter chapter = chapterMapper.toEntity(dtoChapterInsert);
        
        // Set audiobook if provided
        if (dtoChapterInsert.getAudiobookId() != null) {
            Audiobook audiobook = audiobookRepository.findById(dtoChapterInsert.getAudiobookId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
            chapter.setAudiobook(audiobook);
        }
        
        return chapterMapper.toDto(chapterRepository.save(chapter));
    }

    @Override
    public DtoChapter updateChapter(Long id, DtoChapterUpdate dtoChapterUpdate) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Chapter").prepareErrorMessage()));
        
        chapterMapper.updateEntityFromDto(dtoChapterUpdate, chapter);
        
        if (dtoChapterUpdate.getAudiobook() != null && dtoChapterUpdate.getAudiobook().getId() != null) {
            Audiobook audiobook = audiobookRepository.findById(dtoChapterUpdate.getAudiobook().getId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
            chapter.setAudiobook(audiobook);
        }
        
        return chapterMapper.toDto(chapterRepository.save(chapter));
    }

    @Override
    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }
}