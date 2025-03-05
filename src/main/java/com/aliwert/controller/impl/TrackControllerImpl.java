package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.ITrackController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import com.aliwert.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tracks")
@RequiredArgsConstructor
public class TrackControllerImpl extends BaseController implements ITrackController {

    private final TrackService trackService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoTrack>> getAllTracks() {
        return ok(trackService.getAllTracks());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> getTrackById(@PathVariable Long id) {
        return ok(trackService.getTrackById(id));
    }
    
    @GetMapping("/album/{albumId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoTrack>> getTracksByAlbumId(@PathVariable Long albumId) {
        return ok(trackService.getTracksByAlbumId(albumId));
    }
    
    @GetMapping("/genre/{genreId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoTrack>> getTracksByGenreId(@PathVariable Long genreId) {
        return ok(trackService.getTracksByGenreId(genreId));
    }
    
    @GetMapping("/category/{categoryId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoTrack>> getTracksByCategoryId(@PathVariable Long categoryId) {
        return ok(trackService.getTracksByCategoryId(categoryId));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoTrack> createTrack(@RequestBody DtoTrackInsert dtoTrackInsert) {
        return ok(trackService.createTrack(dtoTrackInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> updateTrack(@PathVariable Long id, @RequestBody DtoTrackUpdate dtoTrackUpdate) {
        return ok(trackService.updateTrack(id, dtoTrackUpdate));
    }
    
    @PostMapping("/{trackId}/genres/{genreId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> addGenreToTrack(@PathVariable Long trackId, @PathVariable Long genreId) {
        return ok(trackService.addGenreToTrack(trackId, genreId));
    }
    
    @DeleteMapping("/{trackId}/genres/{genreId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> removeGenreFromTrack(@PathVariable Long trackId, @PathVariable Long genreId) {
        return ok(trackService.removeGenreFromTrack(trackId, genreId));
    }
    
    @PostMapping("/{trackId}/categories/{categoryId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> addCategoryToTrack(@PathVariable Long trackId, @PathVariable Long categoryId) {
        return ok(trackService.addCategoryToTrack(trackId, categoryId));
    }
    
    @DeleteMapping("/{trackId}/categories/{categoryId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoTrack> removeCategoryFromTrack(@PathVariable Long trackId, @PathVariable Long categoryId) {
        return ok(trackService.removeCategoryFromTrack(trackId, categoryId));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return null;
    }
}