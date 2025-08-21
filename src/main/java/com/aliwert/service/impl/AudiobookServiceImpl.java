package com.aliwert.service.impl;

import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.AudiobookMapper;
import com.aliwert.model.Audiobook;
import com.aliwert.repository.AudiobookRepository;
import com.aliwert.service.AudiobookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudiobookServiceImpl implements AudiobookService {

    private final AudiobookRepository audiobookRepository;
    private final AudiobookMapper audiobookMapper;

    @Override
    public List<DtoAudiobook> getAllAudiobooks() {
        return audiobookMapper.toDtoList(audiobookRepository.findAll());
    }

    @Override
    public DtoAudiobook getAudiobookById(Long id) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
        return audiobookMapper.toDto(audiobook);
    }

    @Override
    public DtoAudiobook createAudiobook(DtoAudiobookInsert dtoAudiobookInsert) {
        Audiobook audiobook = audiobookMapper.toEntity(dtoAudiobookInsert);
        return audiobookMapper.toDto(audiobookRepository.save(audiobook));
    }

    @Override
    public DtoAudiobook updateAudiobook(Long id, DtoAudiobookUpdate dtoAudiobookUpdate) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Audiobook").prepareErrorMessage()));
        audiobookMapper.updateEntityFromDto(dtoAudiobookUpdate, audiobook);
        return audiobookMapper.toDto(audiobookRepository.save(audiobook));
    }

    @Override
    public void deleteAudiobook(Long id) {
        audiobookRepository.deleteById(id);
    }
}