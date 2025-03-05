package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IEpisodeController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import com.aliwert.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/episodes")
@RequiredArgsConstructor
public class EpisodeControllerImpl extends BaseController implements IEpisodeController {


    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoEpisode>> getAllEpisodes() {
        return ok(episodeService.getAllEpisodes());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoEpisode> getEpisodeById(@PathVariable Long id) {
        return ok(episodeService.getEpisodeById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoEpisode> createEpisode(@RequestBody DtoEpisodeInsert dtoEpisodeInsert) {
        return ok(episodeService.createEpisode(dtoEpisodeInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoEpisode> updateEpisode(@PathVariable Long id, @RequestBody DtoEpisodeUpdate dtoEpisodeUpdate) {
        return ok(episodeService.updateEpisode(id, dtoEpisodeUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteEpisode(@PathVariable Long id) {
        episodeService.deleteEpisode(id);
        return null;
    }
}