package com.aliwert.controller;

import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import java.util.List;

public interface IAudiobookController {
    RootEntity<List<DtoAudiobook>> getAllAudiobooks();
    RootEntity<DtoAudiobook> getAudiobookById(Long id);
    RootEntity<DtoAudiobook> createAudiobook(DtoAudiobookInsert dtoAudiobookInsert);
    RootEntity<DtoAudiobook> updateAudiobook(Long id, DtoAudiobookUpdate dtoAudiobookUpdate);
    RootEntity<Void> deleteAudiobook(Long id);
}