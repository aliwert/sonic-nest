package com.aliwert.controller;

import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import java.util.List;

public interface IGenreController {
    RootEntity<List<DtoGenre>> getAllGenres();
    RootEntity<DtoGenre> getGenreById(Long id);
    RootEntity<DtoGenre> createGenre(DtoGenreInsert dtoGenreInsert);
    RootEntity<DtoGenre> updateGenre(Long id, DtoGenreUpdate dtoGenreUpdate);
    RootEntity<Void> deleteGenre(Long id);
}