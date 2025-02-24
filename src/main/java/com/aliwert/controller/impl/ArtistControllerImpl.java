package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IArtistController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoArtist;
import com.aliwert.dto.insert.DtoArtistInsert;
import com.aliwert.dto.update.DtoArtistUpdate;
import com.aliwert.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistControllerImpl extends BaseController implements IArtistController {

    private final ArtistService artistService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoArtist>> getAllArtists() {
        return ok(artistService.getAllArtists());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoArtist> getArtistById(@PathVariable Long id) {
        return ok(artistService.getArtistById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoArtist> createArtist(@RequestBody DtoArtistInsert dtoArtistInsert) {
        return ok(artistService.createArtist(dtoArtistInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoArtist> updateArtist(@PathVariable Long id, @RequestBody DtoArtistUpdate dtoArtistUpdate) {
        return ok(artistService.updateArtist(id, dtoArtistUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return null;
    }
}