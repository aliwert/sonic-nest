package com.aliwert.service.impl;

import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Audiobook;
import com.aliwert.repository.AudiobookRepository;
import com.aliwert.service.AudiobookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudiobookServiceImpl implements AudiobookService {

    private final AudiobookRepository audiobookRepository;

    @Override
    public List<DtoAudiobook> getAllAudiobooks() {
        return audiobookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoAudiobook getAudiobookById(Long id) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
        return convertToDto(audiobook);
    }

    @Override
    public DtoAudiobook createAudiobook(DtoAudiobookInsert dtoAudiobookInsert) {
        Audiobook audiobook = new Audiobook();
        updateAudiobookFromDto(audiobook, dtoAudiobookInsert);
        return convertToDto(audiobookRepository.save(audiobook));
    }

    @Override
    public DtoAudiobook updateAudiobook(Long id, DtoAudiobookUpdate dtoAudiobookUpdate) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
        updateAudiobookFromDto(audiobook, dtoAudiobookUpdate);
        return convertToDto(audiobookRepository.save(audiobook));
    }

    @Override
    public void deleteAudiobook(Long id) {
        audiobookRepository.deleteById(id);
    }

    private DtoAudiobook convertToDto(Audiobook audiobook) {
        DtoAudiobook dto = new DtoAudiobook();
        dto.setId(audiobook.getId());
        dto.setTitle(audiobook.getTitle());
        dto.setAuthor(audiobook.getAuthor());
        dto.setNarrator(audiobook.getNarrator());
        dto.setDescription(audiobook.getDescription());
        dto.setPublisher(audiobook.getPublisher());
        dto.setDuration(audiobook.getDuration());
        dto.setImageUrl(audiobook.getImageUrl());
        dto.setCreateTime((Date) audiobook.getCreatedTime());
        dto.setChapterIds(audiobook.getChapters().stream().map(chapter -> chapter.getId()).collect(Collectors.toList()));
        return dto;
    }

    private void updateAudiobookFromDto(Audiobook audiobook, Object dto) {
        if (dto instanceof DtoAudiobookInsert insert) {
            audiobook.setTitle(insert.getTitle());
            audiobook.setAuthor(insert.getAuthor());
            audiobook.setNarrator(insert.getNarrator());
            audiobook.setDescription(insert.getDescription());
            audiobook.setPublisher(insert.getPublisher());
            audiobook.setDuration(insert.getDuration());
            audiobook.setImageUrl(insert.getImageUrl());
        } else if (dto instanceof DtoAudiobookUpdate update) {
            audiobook.setTitle(update.getTitle());
            audiobook.setAuthor(update.getAuthor());
            audiobook.setNarrator(update.getNarrator());
            audiobook.setDescription(update.getDescription());
            audiobook.setPublisher(update.getPublisher());
            audiobook.setDuration(update.getDuration());
            audiobook.setImageUrl(update.getImageUrl());
        }
    }
}