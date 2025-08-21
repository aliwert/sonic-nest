package com.aliwert.service.impl;

import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.GenreMapper;
import com.aliwert.model.Genre;
import com.aliwert.repository.GenreRepository;
import com.aliwert.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<DtoGenre> getAllGenres() {
        return genreMapper.toDtoList(genreRepository.findAll());
    }

    @Override
    public DtoGenre getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        return genreMapper.toDto(genre);
    }

    @Override
    public DtoGenre createGenre(DtoGenreInsert dtoGenreInsert) {
        Genre genre = genreMapper.toEntity(dtoGenreInsert);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Override
    public DtoGenre updateGenre(Long id, DtoGenreUpdate dtoGenreUpdate) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        genreMapper.updateEntityFromDto(dtoGenreUpdate, genre);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}