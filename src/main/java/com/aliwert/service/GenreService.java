package com.aliwert.service;

import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import java.util.List;

public interface GenreService {
    List<DtoGenre> getAllGenres();
    DtoGenre getGenreById(Long id);
    DtoGenre createGenre(DtoGenreInsert dtoGenreInsert);
    DtoGenre updateGenre(Long id, DtoGenreUpdate dtoGenreUpdate);
    void deleteGenre(Long id);
}