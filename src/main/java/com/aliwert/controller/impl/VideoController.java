package com.aliwert.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliwert.dto.DtoVideo;
import com.aliwert.dto.insert.DtoVideoInsert;
import com.aliwert.dto.update.DtoVideoUpdate;
import com.aliwert.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    
    @PostMapping("/upload")
    public ResponseEntity<DtoVideo> uploadVideo(
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "videoType", required = false) String videoType,
            @RequestParam("artistId") Long artistId,
            @RequestParam(value = "albumId", required = false) Long albumId,
            @RequestParam(value = "genreIds", required = false) List<Long> genreIds) {
        
        DtoVideoInsert dtoVideoInsert = new DtoVideoInsert();
        dtoVideoInsert.setTitle(title);
        dtoVideoInsert.setDescription(description);
        dtoVideoInsert.setVideoType(videoType);
        dtoVideoInsert.setArtistId(artistId);
        dtoVideoInsert.setAlbumId(albumId);
        dtoVideoInsert.setGenreIds(genreIds);
        
        DtoVideo createdVideo = videoService.uploadVideo(dtoVideoInsert, videoFile, thumbnailFile);
        return ResponseEntity.ok(createdVideo);
    }
    
    @GetMapping("/get-videos")
    public ResponseEntity<List<DtoVideo>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }
    
    @GetMapping("/get-video-by-id/{id}")
    public ResponseEntity<DtoVideo> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }
    
    @GetMapping("/{id}/play")
    public ResponseEntity<DtoVideo> playVideo(@PathVariable Long id) {
        videoService.incrementViewCount(id);
        return ResponseEntity.ok(videoService.getVideoById(id));
    }
    
    @PutMapping("/update{id}")
    public ResponseEntity<DtoVideo> updateVideo(
            @PathVariable Long id,
            @RequestBody DtoVideoUpdate dtoVideoUpdate) {
        return ResponseEntity.ok(videoService.updateVideo(id, dtoVideoUpdate));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<DtoVideo>> getVideosByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(videoService.getVideosByArtist(artistId));
    }
    
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<DtoVideo>> getVideosByAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(videoService.getVideosByAlbum(albumId));
    }
    
}
