package com.aliwert.service;

import com.aliwert.dto.DtoAlbum;
import com.aliwert.dto.insert.DtoAlbumInsert;
import com.aliwert.dto.update.DtoAlbumUpdate;

import java.util.List;

public interface AlbumService {
    List<DtoAlbum> getAllAlbums();
    DtoAlbum getAlbumById(Long id);
    DtoAlbum createAlbum(DtoAlbumInsert dtoAlbumInsert);
    DtoAlbum updateAlbum(Long id, DtoAlbumUpdate dtoAlbumUpdate);
    void deleteAlbum(Long id);
}