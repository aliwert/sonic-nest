package com.aliwert.controller;

import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import java.util.List;

public interface ITrackController {
    RootEntity<List<DtoTrack>> getAllTracks();
    RootEntity<DtoTrack> getTrackById(Long id);
    RootEntity<List<DtoTrack>> getTracksByAlbumId(Long albumId);
    RootEntity<List<DtoTrack>> getTracksByGenreId(Long genreId);
    RootEntity<List<DtoTrack>> getTracksByCategoryId(Long categoryId);
    RootEntity<DtoTrack> createTrack(DtoTrackInsert dtoTrackInsert);
    RootEntity<DtoTrack> updateTrack(Long id, DtoTrackUpdate dtoTrackUpdate);
    RootEntity<DtoTrack> addGenreToTrack(Long trackId, Long genreId);
    RootEntity<DtoTrack> removeGenreFromTrack(Long trackId, Long genreId);
    RootEntity<DtoTrack> addCategoryToTrack(Long trackId, Long categoryId);
    RootEntity<DtoTrack> removeCategoryFromTrack(Long trackId, Long categoryId);
    RootEntity<Void> deleteTrack(Long id);
}