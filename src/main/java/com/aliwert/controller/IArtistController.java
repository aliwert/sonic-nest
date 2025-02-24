package com.aliwert.controller;

import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import java.util.List;

public interface IArtistController {
    RootEntity<List<DtoArtist>> getAllArtists();
    RootEntity<DtoArtist> getArtistById(Long id);
    RootEntity<DtoArtist> createArtist(DtoArtistInsert dtoArtistInsert);
    RootEntity<DtoArtist> updateArtist(Long id, DtoArtistUpdate dtoArtistUpdate);
    RootEntity<Void> deleteArtist(Long id);
}