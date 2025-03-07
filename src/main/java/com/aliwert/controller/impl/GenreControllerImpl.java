package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IGenreController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoGenre;
import com.aliwert.dto.insert.DtoGenreInsert;
import com.aliwert.dto.update.DtoGenreUpdate;
import com.aliwert.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreControllerImpl extends BaseController implements IGenreController {

    private final GenreService genreService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoGenre>> getAllGenres() {
        return ok(genreService.getAllGenres());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoGenre> getGenreById(@PathVariable Long id) {
        return ok(genreService.getGenreById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoGenre> createGenre(@RequestBody DtoGenreInsert dtoGenreInsert) {
        return ok(genreService.createGenre(dtoGenreInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoGenre> updateGenre(@PathVariable Long id, @RequestBody DtoGenreUpdate dtoGenreUpdate) {
        return ok(genreService.updateGenre(id, dtoGenreUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return null;
    }
}