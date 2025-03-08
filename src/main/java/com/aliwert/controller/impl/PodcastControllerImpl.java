package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IPodcastController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import com.aliwert.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcasts")
@RequiredArgsConstructor
public class PodcastControllerImpl extends BaseController implements IPodcastController {

    private final PodcastService podcastService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPodcast>> getAllPodcasts() {
        return ok(podcastService.getAllPodcasts());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcast> getPodcastById(@PathVariable Long id) {
        return ok(podcastService.getPodcastById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoPodcast> createPodcast(@RequestBody DtoPodcastInsert dtoPodcastInsert) {
        return ok(podcastService.createPodcast(dtoPodcastInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcast> updatePodcast(@PathVariable Long id, @RequestBody DtoPodcastUpdate dtoPodcastUpdate) {
        return ok(podcastService.updatePodcast(id, dtoPodcastUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deletePodcast(@PathVariable Long id) {
        podcastService.deletePodcast(id);
        return null;
    }

    @PostMapping("/{podcastId}/categories/{categoryId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcast> addCategoryToPodcast(@PathVariable Long podcastId, @PathVariable Long categoryId) {
        return ok(podcastService.addCategoryToPodcast(podcastId, categoryId));
    }

    @DeleteMapping("/{podcastId}/categories/{categoryId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPodcast> removeCategoryFromPodcast(@PathVariable Long podcastId, @PathVariable Long categoryId) {
        return ok(podcastService.removeCategoryFromPodcast(podcastId, categoryId));
    }
}