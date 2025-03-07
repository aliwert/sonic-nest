package com.aliwert.service.impl;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.insert.DtoTrackInsert;
import com.aliwert.dto.update.DtoTrackUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final CategoryRepository categoryRepository;
    private final AlbumServiceImpl albumService;
    private final GenreServiceImpl genreService;
    private final CategoryServiceImpl categoryService;

    @Override
    public List<DtoTrack> getAllTracks() {
        return trackRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoTrack getTrackById(Long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        return convertToDto(track);
    }

    @Override
    public List<DtoTrack> getTracksByAlbumId(Long albumId) {
        return trackRepository.findByAlbumId(albumId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoTrack> getTracksByGenreId(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        return trackRepository.findByGenresContaining(genre).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoTrack> getTracksByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        return trackRepository.findByCategoriesContaining(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoTrack createTrack(DtoTrackInsert dtoTrackInsert) {
        Track track = new Track();
        updateTrackFromDto(track, dtoTrackInsert);
        return convertToDto(trackRepository.save(track));
    }

    @Override
    public DtoTrack updateTrack(Long id, DtoTrackUpdate dtoTrackUpdate) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        updateTrackFromDto(track, dtoTrackUpdate);
        return convertToDto(trackRepository.save(track));
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

        return convertToDto(track);
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

        return convertToDto(track);
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

        return convertToDto(track);
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

        return convertToDto(track);
    }

    public DtoTrack convertToDto(Track track) {
        DtoTrack dto = new DtoTrack();
        dto.setId(track.getId());
        dto.setTitle(track.getTitle());
        dto.setDuration(track.getDuration());
        dto.setAudioUrl(track.getAudioUrl());

        if (track.getCreatedTime() != null) {
            if (track.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) track.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(track.getCreatedTime().getTime()));
            }
        }

        // album
        if (track.getAlbum() != null) {
            DtoAlbum albumDto = new DtoAlbum();
            albumDto.setId(track.getAlbum().getId());
            albumDto.setTitle(track.getAlbum().getTitle());
            albumDto.setReleaseDate(track.getAlbum().getReleaseDate());
            albumDto.setImageUrl(track.getAlbum().getImageUrl());

            dto.setAlbum(albumDto);
        }

        if (track.getGenres() != null) {
            List<DtoGenre> genres = track.getGenres().stream().map(genre -> {
                DtoGenre genreDto = new DtoGenre();
                genreDto.setId(genre.getId());
                genreDto.setName(genre.getName());
                genreDto.setDescription(genre.getDescription());
                return genreDto;
            }).collect(Collectors.toList());
            dto.setGenres(genres);
        }

        if (track.getCategories() != null) {
            List<DtoCategory> categories = track.getCategories().stream().map(category -> {
                DtoCategory categoryDto = new DtoCategory();
                categoryDto.setId(category.getId());
                categoryDto.setName(category.getName());
                categoryDto.setDescription(category.getDescription());
                categoryDto.setImageUrl(category.getImageUrl());

                return categoryDto;
            }).collect(Collectors.toList());
            dto.setCategories(categories);
        }
        return dto;
    }

    private void updateTrackFromDto(Track track, Object dto) {
        if (dto instanceof DtoTrackInsert insert) {
            track.setTitle(insert.getTitle());
            track.setDuration(insert.getDuration());
            track.setAudioUrl(insert.getAudioUrl());

            if (insert.getAlbumId() != null) {
                Album album = albumRepository.findById(insert.getAlbumId())
                        .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
                track.setAlbum(album);
            }

            if (insert.getGenreIds() != null && !insert.getGenreIds().isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(insert.getGenreIds());
                track.setGenres(genres);
            }

            if (insert.getCategoryIds() != null && !insert.getCategoryIds().isEmpty()) {
                List<Category> categories = categoryRepository.findAllById(insert.getCategoryIds());
                track.setCategories(categories);
            }

        } else if (dto instanceof DtoTrackUpdate update) {
            track.setTitle(update.getTitle());
            track.setDuration(update.getDuration());
            track.setAudioUrl(update.getAudioUrl());

            if (update.getAlbumId() != null) {
                Album album = albumRepository.findById(update.getAlbumId())
                        .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
                track.setAlbum(album);
            }

            if (update.getGenreIds() != null) {
                List<Genre> genres = genreRepository.findAllById(update.getGenreIds());
                track.setGenres(genres);
            }

            if (update.getCategoryIds() != null) {
                List<Category> categories = categoryRepository.findAllById(update.getCategoryIds());
                track.setCategories(categories);
            }
        }
    }
}