package com.aliwert.service;

import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import java.util.List;

public interface PodcastEpisodeService {
    List<DtoPodcastEpisode> getAllPodcastEpisodes();
    List<DtoPodcastEpisode> getEpisodesByPodcastId(Long podcastId);
    DtoPodcastEpisode getPodcastEpisodeById(Long id);
    DtoPodcastEpisode createPodcastEpisode(DtoPodcastEpisodeInsert dtoPodcastEpisodeInsert);
    DtoPodcastEpisode updatePodcastEpisode(Long id, DtoPodcastEpisodeUpdate dtoPodcastEpisodeUpdate);
    void deletePodcastEpisode(Long id);
}