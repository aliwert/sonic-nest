package com.aliwert.controller;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import java.util.List;

public interface IEpisodeController {
    RootEntity<List<DtoEpisode>> getAllEpisodes();
    RootEntity<DtoEpisode> getEpisodeById(Long id);
    RootEntity<DtoEpisode> createEpisode(DtoEpisodeInsert dtoEpisodeInsert);
    RootEntity<DtoEpisode> updateEpisode(Long id, DtoEpisodeUpdate dtoEpisodeUpdate);
    RootEntity<Void> deleteEpisode(Long id);
}