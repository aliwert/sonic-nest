package com.aliwert.controller;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;
import java.util.List;

public interface IAlbumController {
    RootEntity<List<DtoAlbum>> getAllAlbums();
    RootEntity<DtoAlbum> getAlbumById(Long id);
    RootEntity<DtoAlbum> createAlbum(DtoAlbumInsert dtoAlbumInsert);
    RootEntity<DtoAlbum> updateAlbum(Long id,DtoAlbumUpdate dtoAlbumUpdate);
    RootEntity<Void> deleteAlbum(Long id);
}