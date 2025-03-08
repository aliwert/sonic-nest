package com.aliwert.controller;

import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import java.util.List;

public interface IPodcastController {
    RootEntity<List<DtoPodcast>> getAllPodcasts();
    RootEntity<DtoPodcast> getPodcastById(Long id);
    RootEntity<DtoPodcast> createPodcast(DtoPodcastInsert dtoPodcastInsert);
    RootEntity<DtoPodcast> updatePodcast(Long id, DtoPodcastUpdate dtoPodcastUpdate);
    RootEntity<DtoPodcast> addCategoryToPodcast(Long podcastId, Long categoryId);
    RootEntity<DtoPodcast> removeCategoryFromPodcast(Long podcastId, Long categoryId);
    RootEntity<Void> deletePodcast(Long id);
}