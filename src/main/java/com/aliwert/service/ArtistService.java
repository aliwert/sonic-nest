package com.aliwert.service;

import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import java.util.List;

public interface ArtistService {
    List<DtoArtist> getAllArtists();
    DtoArtist getArtistById(Long id);
    DtoArtist createArtist(DtoArtistInsert dtoArtistInsert);
    DtoArtist updateArtist(Long id, DtoArtistUpdate dtoArtistUpdate);
    void deleteArtist(Long id);
}