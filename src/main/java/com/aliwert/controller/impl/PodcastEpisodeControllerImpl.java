package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IPodcastEpisodeController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import com.aliwert.service.PodcastEpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcast-episodes")
@RequiredArgsConstructor
public class PodcastEpisodeControllerImpl extends BaseController implements IPodcastEpisodeController {

    private final PodcastEpisodeService podcastEpisodeService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPodcastEpisode>> getAllPodcastEpisodes() {
        return ok(podcastEpisodeService.getAllPodcastEpisodes());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcastEpisode> getPodcastEpisodeById(@PathVariable Long id) {
        return ok(podcastEpisodeService.getPodcastEpisodeById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoPodcastEpisode> createPodcastEpisode(@RequestBody DtoPodcastEpisodeInsert dtoPodcastEpisodeInsert) {
        return ok(podcastEpisodeService.createPodcastEpisode(dtoPodcastEpisodeInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcastEpisode> updatePodcastEpisode(@PathVariable Long id,
                                                              @RequestBody DtoPodcastEpisodeUpdate dtoPodcastEpisodeUpdate) {
        return ok(podcastEpisodeService.updatePodcastEpisode(id, dtoPodcastEpisodeUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deletePodcastEpisode(@PathVariable Long id) {
        podcastEpisodeService.deletePodcastEpisode(id);
        return null;
    }

    @GetMapping("/podcast/{podcastId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPodcastEpisode>> getEpisodesByPodcastId(@PathVariable Long podcastId) {
        return ok(podcastEpisodeService.getEpisodesByPodcastId(podcastId));
    }
}