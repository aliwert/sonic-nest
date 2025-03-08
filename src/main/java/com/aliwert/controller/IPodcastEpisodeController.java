package com.aliwert.controller;

import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import java.util.List;

public interface IPodcastEpisodeController {
    RootEntity<List<DtoPodcastEpisode>> getAllPodcastEpisodes();
    RootEntity<List<DtoPodcastEpisode>> getEpisodesByPodcastId(Long podcastId);
    RootEntity<DtoPodcastEpisode> getPodcastEpisodeById(Long id);
    RootEntity<DtoPodcastEpisode> createPodcastEpisode(DtoPodcastEpisodeInsert dtoPodcastEpisodeInsert);
    RootEntity<DtoPodcastEpisode> updatePodcastEpisode(Long id, DtoPodcastEpisodeUpdate dtoPodcastEpisodeUpdate);
    RootEntity<Void> deletePodcastEpisode(Long id);
}