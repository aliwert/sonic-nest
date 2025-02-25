package com.aliwert.service;

import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import java.util.List;

public interface AudiobookService {
    List<DtoAudiobook> getAllAudiobooks();
    DtoAudiobook getAudiobookById(Long id);
    DtoAudiobook createAudiobook(DtoAudiobookInsert dtoAudiobookInsert);
    DtoAudiobook updateAudiobook(Long id, DtoAudiobookUpdate dtoAudiobookUpdate);
    void deleteAudiobook(Long id);
}