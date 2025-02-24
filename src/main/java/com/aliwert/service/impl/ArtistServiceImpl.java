package com.aliwert.service.impl;

import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Artist;
import com.aliwert.repository.ArtistRepository;
import com.aliwert.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    public List<DtoArtist> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoArtist getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
        return convertToDto(artist);
    }

    @Override
    public DtoArtist createArtist(DtoArtistInsert dtoArtistInsert) {
        Artist artist = new Artist();
        updateArtistFromDto(artist, dtoArtistInsert);
        return convertToDto(artistRepository.save(artist));
    }

    @Override
    public DtoArtist updateArtist(Long id, DtoArtistUpdate dtoArtistUpdate) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
        updateArtistFromDto(artist, dtoArtistUpdate);
        return convertToDto(artistRepository.save(artist));
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    private DtoArtist convertToDto(Artist artist) {
        DtoArtist dto = new DtoArtist();
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setBiography(artist.getBiography());
        dto.setImageUrl(artist.getImageUrl());
        dto.setCreateTime((Date) artist.getCreatedTime());
        dto.setAlbumIds(artist.getAlbums().stream().map(album -> album.getId()).collect(Collectors.toList()));
        return dto;
    }

    private void updateArtistFromDto(Artist artist, Object dto) {
        if (dto instanceof DtoArtistInsert insert) {
            artist.setName(insert.getName());
            artist.setBiography(insert.getBiography());
            artist.setImageUrl(insert.getImageUrl());
        } else if (dto instanceof DtoArtistUpdate update) {
            artist.setName(update.getName());
            artist.setBiography(update.getBiography());
            artist.setImageUrl(update.getImageUrl());
        }
    }
}