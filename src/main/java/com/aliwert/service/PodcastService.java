package com.aliwert.service;

import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import java.util.List;

public interface PodcastService {
    List<DtoPodcast> getAllPodcasts();
    DtoPodcast getPodcastById(Long id);
    DtoPodcast createPodcast(DtoPodcastInsert dtoPodcastInsert);
    DtoPodcast updatePodcast(Long id, DtoPodcastUpdate dtoPodcastUpdate);
    void deletePodcast(Long id);
    DtoPodcast addCategoryToPodcast(Long podcastId, Long categoryId);
    DtoPodcast removeCategoryFromPodcast(Long podcastId, Long categoryId);
}