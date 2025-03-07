package com.aliwert.service.impl;

import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Genre;
import com.aliwert.repository.GenreRepository;
import com.aliwert.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<DtoGenre> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoGenre getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        return convertToDto(genre);
    }

    @Override
    public DtoGenre createGenre(DtoGenreInsert dtoGenreInsert) {
        Genre genre = new Genre();
        updateGenreFromDto(genre, dtoGenreInsert);
        return convertToDto(genreRepository.save(genre));
    }

    @Override
    public DtoGenre updateGenre(Long id, DtoGenreUpdate dtoGenreUpdate) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Genre").prepareErrorMessage()));
        updateGenreFromDto(genre, dtoGenreUpdate);
        return convertToDto(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    private DtoGenre convertToDto(Genre genre) {
        DtoGenre dto = new DtoGenre();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());

        // Handle creation time safely
        if (genre.getCreatedTime() != null) {
            if (genre.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) genre.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(genre.getCreatedTime().getTime()));
            }
        }

        return dto;
    }

    private void updateGenreFromDto(Genre genre, Object dto) {
        if (dto instanceof DtoGenreInsert insert) {
            genre.setName(insert.getName());
            genre.setDescription(insert.getDescription());
        } else if (dto instanceof DtoGenreUpdate update) {
            genre.setName(update.getName());
            genre.setDescription(update.getDescription());
        }
    }
}