package com.aliwert.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliwert.dto.DtoVideo;
import com.aliwert.dto.insert.DtoVideoInsert;
import com.aliwert.dto.update.DtoVideoUpdate;

public interface VideoService {
    List<DtoVideo> getAllVideos();
    DtoVideo getVideoById(Long id);
    DtoVideo uploadVideo(DtoVideoInsert dtoVideoInsert, MultipartFile videoFile, MultipartFile thumbnailFile);
    DtoVideo updateVideo(Long id, DtoVideoUpdate dtoVideoUpdate);
    List<DtoVideo> getVideosByArtist(Long artistId);
    List<DtoVideo> getVideosByAlbum(Long albumId);
    void deleteVideo(Long id);
    void incrementViewCount(Long id);
}