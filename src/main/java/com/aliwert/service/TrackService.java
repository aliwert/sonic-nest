package com.aliwert.service;

import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import java.util.List;

public interface TrackService {
    List<DtoTrack> getAllTracks();
    DtoTrack getTrackById(Long id);
    List<DtoTrack> getTracksByAlbumId(Long albumId);
    List<DtoTrack> getTracksByGenreId(Long genreId);
    List<DtoTrack> getTracksByCategoryId(Long categoryId);
    DtoTrack createTrack(DtoTrackInsert dtoTrackInsert);
    DtoTrack updateTrack(Long id, DtoTrackUpdate dtoTrackUpdate);
    void deleteTrack(Long id);
    
    // genre and category relation management
    DtoTrack addGenreToTrack(Long trackId, Long genreId);
    DtoTrack removeGenreFromTrack(Long trackId, Long genreId);
    DtoTrack addCategoryToTrack(Long trackId, Long categoryId);
    DtoTrack removeCategoryFromTrack(Long trackId, Long categoryId);
}