package com.aliwert.service;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import java.util.List;

public interface EpisodeService {
    List<DtoEpisode> getAllEpisodes();
    DtoEpisode getEpisodeById(Long id);
    DtoEpisode createEpisode(DtoEpisodeInsert dtoEpisodeInsert);
    DtoEpisode updateEpisode(Long id, DtoEpisodeUpdate dtoEpisodeUpdate);
    void deleteEpisode(Long id);
}