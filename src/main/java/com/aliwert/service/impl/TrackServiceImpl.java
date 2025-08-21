package com.aliwert.service.impl;

import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.TrackMapper;
import com.aliwert.model.Album;
import com.aliwert.model.Category;
import com.aliwert.model.Genre;
import com.aliwert.model.Track;
import com.aliwert.repository.AlbumRepository;
import com.aliwert.repository.CategoryRepository;
import com.aliwert.repository.GenreRepository;
import com.aliwert.repository.TrackRepository;
import com.aliwert.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final CategoryRepository categoryRepository;
    private final TrackMapper trackMapper;

    @Override
    public List<DtoTrack> getAllTracks() {
        return trackMapper.toDtoList(trackRepository.findAll());
    }

    @Override
    public DtoTrack getTrackById(Long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        return trackMapper.toDto(track);
    }

    @Override
    public List<DtoTrack> getTracksByAlbumId(Long albumId) {
        return trackMapper.toDtoList(trackRepository.findByAlbumId(albumId));
    }

    @Override
    public List<DtoTrack> getTracksByGenreId(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        return trackMapper.toDtoList(trackRepository.findByGenresContaining(genre));
    }

    @Override
    public List<DtoTrack> getTracksByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        return trackMapper.toDtoList(trackRepository.findByCategoriesContaining(category));
    }

    @Override
    public DtoTrack createTrack(DtoTrackInsert dtoTrackInsert) {
        Track track = trackMapper.toEntity(dtoTrackInsert);
        
        if (dtoTrackInsert.getAlbumId() != null) {
            Album album = albumRepository.findById(dtoTrackInsert.getAlbumId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
            track.setAlbum(album);
        }

        if (dtoTrackInsert.getGenreIds() != null && !dtoTrackInsert.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(dtoTrackInsert.getGenreIds());
            track.setGenres(genres);
        }

        if (dtoTrackInsert.getCategoryIds() != null && !dtoTrackInsert.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dtoTrackInsert.getCategoryIds());
            track.setCategories(categories);
        }
        
        return trackMapper.toDto(trackRepository.save(track));
    }

    @Override
    public DtoTrack updateTrack(Long id, DtoTrackUpdate dtoTrackUpdate) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        
        trackMapper.updateEntityFromDto(dtoTrackUpdate, track);
        
        if (dtoTrackUpdate.getAlbumId() != null) {
            Album album = albumRepository.findById(dtoTrackUpdate.getAlbumId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
            track.setAlbum(album);
        }

        if (dtoTrackUpdate.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(dtoTrackUpdate.getGenreIds());
            track.setGenres(genres);
        }

        if (dtoTrackUpdate.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dtoTrackUpdate.getCategoryIds());
            track.setCategories(categories);
        }
        
        return trackMapper.toDto(trackRepository.save(track));
    }

    @Override
    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    @Override
    public DtoTrack addGenreToTrack(Long trackId, Long genreId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));

        if (track.getGenres() == null) {
            track.setGenres(new ArrayList<>());
        }

        if (!track.getGenres().contains(genre)) {
            track.getGenres().add(genre);
            trackRepository.save(track);
        }

        return trackMapper.toDto(track);
    }

    @Override
    public DtoTrack removeGenreFromTrack(Long trackId, Long genreId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));

        if (track.getGenres() != null) {
            track.getGenres().remove(genre);
            trackRepository.save(track);
        }

        return trackMapper.toDto(track);
    }

    @Override
    public DtoTrack addCategoryToTrack(Long trackId, Long categoryId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));

        if (track.getCategories() == null) {
            track.setCategories(new ArrayList<>());
        }

        if (!track.getCategories().contains(category)) {
            track.getCategories().add(category);
            trackRepository.save(track);
        }

        return trackMapper.toDto(track);
    }

    @Override
    public DtoTrack removeCategoryFromTrack(Long trackId, Long categoryId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));

        if (track.getCategories() != null) {
            track.getCategories().remove(category);
            trackRepository.save(track);
        }

        return trackMapper.toDto(track);
    }
}