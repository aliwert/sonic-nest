package com.aliwert.service.impl;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Album;
import com.aliwert.model.Artist;
import com.aliwert.model.Genre;
import com.aliwert.repository.AlbumRepository;
import com.aliwert.repository.ArtistRepository;
import com.aliwert.repository.GenreRepository;
import com.aliwert.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<DtoAlbum> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoAlbum getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
        return convertToDto(album);
    }

    @Override
    public DtoAlbum createAlbum(DtoAlbumInsert dtoAlbumInsert) {
        Album album = new Album();
        updateAlbumFromDto(album, dtoAlbumInsert);
        return convertToDto(albumRepository.save(album));
    }

    @Override
    public DtoAlbum updateAlbum(Long id, DtoAlbumUpdate dtoAlbumUpdate) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
        updateAlbumFromDto(album, dtoAlbumUpdate);
        return convertToDto(albumRepository.save(album));
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    private DtoAlbum convertToDto(Album album) {
        DtoAlbum dto = new DtoAlbum();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setImageUrl(album.getImageUrl());

        // safely handle artist - might be null for a new album
        if (album.getArtist() != null) {
            dto.setArtistId(album.getArtist().getId());
        }

        // safely handle genres - might be null for a new album
        if (album.getGenres() != null) {
            dto.setGenreIds(album.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setGenreIds(Collections.emptyList());
        }

        // handle creation time if needed
        if (album.getCreatedTime() != null) {
            if (album.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) album.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(album.getCreatedTime().getTime()));
            }
        }

        return dto;
    }

    private void updateAlbumFromDto(Album album, Object dto) {
        if (dto instanceof DtoAlbumInsert insert) {
            updateAlbumFields(album, insert.getTitle(), insert.getReleaseDate(),
                    insert.getImageUrl(), insert.getArtistId(), insert.getGenreIds());
        } else if (dto instanceof DtoAlbumUpdate update) {
            updateAlbumFields(album, update.getTitle(), update.getReleaseDate(),
                    update.getImageUrl(), update.getArtistId(), update.getGenreIds());
        }
    }

    private void updateAlbumFields(Album album, String title, LocalDate releaseDate,
                                   String imageUrl, Long artistId, List<Long> genreIds) {
        album.setTitle(title);
        album.setReleaseDate(releaseDate);
        album.setImageUrl(imageUrl);

        // only set artist if artistId is not null
        if (artistId != null) {
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
            album.setArtist(artist);
        }

        // only set genres if genreIds is not null
        if (genreIds != null && !genreIds.isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(genreIds);
            album.setGenres(genres);
        } else {
            album.setGenres(new ArrayList<>());
        }
    }
}