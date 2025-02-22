package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IAlbumController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;
import com.aliwert.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumControllerImpl extends BaseController implements IAlbumController {

    private final AlbumService albumService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoAlbum>> getAllAlbums() {
        return ok(albumService.getAllAlbums());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoAlbum> getAlbumById(@PathVariable Long id) {
        return ok(albumService.getAlbumById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoAlbum> createAlbum(@RequestBody DtoAlbumInsert dtoAlbumInsert) {
        return ok(albumService.createAlbum(dtoAlbumInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoAlbum> updateAlbum(@PathVariable(name = "id") Long id,    @RequestBody DtoAlbumUpdate dtoAlbumUpdate) {
        return ok(albumService.updateAlbum(id, dtoAlbumUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return null;
    }
}