package com.aliwert.service.impl;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.AlbumMapper;
import com.aliwert.model.Album;
import com.aliwert.model.Artist;
import com.aliwert.model.Genre;
import com.aliwert.repository.AlbumRepository;
import com.aliwert.repository.ArtistRepository;
import com.aliwert.repository.GenreRepository;
import com.aliwert.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final AlbumMapper albumMapper;

    @Override
    public List<DtoAlbum> getAllAlbums() {
        return albumMapper.toDtoList(albumRepository.findAll());
    }

    @Override
    public DtoAlbum getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
        return albumMapper.toDto(album);
    }

    @Override
    public DtoAlbum createAlbum(DtoAlbumInsert dtoAlbumInsert) {
        Album album = albumMapper.toEntity(dtoAlbumInsert);
        
        if (dtoAlbumInsert.getArtistId() != null) {
            Artist artist = artistRepository.findById(dtoAlbumInsert.getArtistId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
            album.setArtist(artist);
        }

        if (dtoAlbumInsert.getGenreIds() != null && !dtoAlbumInsert.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(dtoAlbumInsert.getGenreIds());
            album.setGenres(genres);
        } else {
            album.setGenres(new ArrayList<>());
        }
        
        return albumMapper.toDto(albumRepository.save(album));
    }

    @Override
    public DtoAlbum updateAlbum(Long id, DtoAlbumUpdate dtoAlbumUpdate) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
        
        albumMapper.updateEntityFromDto(dtoAlbumUpdate, album);
        
        if (dtoAlbumUpdate.getArtistId() != null) {
            Artist artist = artistRepository.findById(dtoAlbumUpdate.getArtistId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
            album.setArtist(artist);
        }

        if (dtoAlbumUpdate.getGenreIds() != null) {
            if (!dtoAlbumUpdate.getGenreIds().isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(dtoAlbumUpdate.getGenreIds());
                album.setGenres(genres);
            } else {
                album.setGenres(new ArrayList<>());
            }
        }
        
        return albumMapper.toDto(albumRepository.save(album));
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}