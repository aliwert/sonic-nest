package com.aliwert.service.impl;

import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.ArtistMapper;
import com.aliwert.model.Artist;
import com.aliwert.repository.ArtistRepository;
import com.aliwert.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Override
    public List<DtoArtist> getAllArtists() {
        return artistMapper.toDtoList(artistRepository.findAll());
    }

    @Override
    public DtoArtist getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
        return artistMapper.toDto(artist);
    }

    @Override
    public DtoArtist createArtist(DtoArtistInsert dtoArtistInsert) {
        Artist artist = artistMapper.toEntity(dtoArtistInsert);
        return artistMapper.toDto(artistRepository.save(artist));
    }

    @Override
    public DtoArtist updateArtist(Long id, DtoArtistUpdate dtoArtistUpdate) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
        artistMapper.updateEntityFromDto(dtoArtistUpdate, artist);
        return artistMapper.toDto(artistRepository.save(artist));
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}