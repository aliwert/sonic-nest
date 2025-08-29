package com.aliwert.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliwert.dto.DtoVideo;
import com.aliwert.dto.insert.DtoVideoInsert;
import com.aliwert.dto.update.DtoVideoUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.VideoMapper;
import com.aliwert.model.Album;
import com.aliwert.model.Artist;
import com.aliwert.model.Genre;
import com.aliwert.model.Video;
import com.aliwert.repository.AlbumRepository;
import com.aliwert.repository.ArtistRepository;
import com.aliwert.repository.GenreRepository;
import com.aliwert.repository.VideoRepository;
import com.aliwert.service.VideoService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final VideoMapper videoMapper;
    private final FileUploadService fileUploadService;
    
    @Override
    public List<DtoVideo> getAllVideos() {
        return videoMapper.toDtoList(videoRepository.findAll());
    }
    
    @Override
    public DtoVideo getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    new ErrorMessage(MessageType.NOT_FOUND, "Video").prepareErrorMessage()));
        return videoMapper.toDto(video);
    }
    
    @Override
    @Transactional
    public DtoVideo uploadVideo(DtoVideoInsert dtoVideoInsert, 
                               MultipartFile videoFile, 
                               MultipartFile thumbnailFile) {
        try {
            // upload video file
            String videoFileName = fileUploadService.uploadVideo(videoFile);

            // if thumbnail exists
            String thumbnailFileName = null;
            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                thumbnailFileName = fileUploadService.uploadThumbnail(thumbnailFile);
            }
            
            // video entity
            Video video = videoMapper.toEntity(dtoVideoInsert);
            video.setVideoUrl("/uploads/videos/" + videoFileName);
            if (thumbnailFileName != null) {
                video.setThumbnailUrl("/uploads/thumbnails/" + thumbnailFileName);
            }
            video.setUploadDate(LocalDate.now());
            video.setViewCount(0L);
            
            if (dtoVideoInsert.getArtistId() != null) {
                Artist artist = artistRepository.findById(dtoVideoInsert.getArtistId())
                        .orElseThrow(() -> new RuntimeException(
                            new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
                video.setArtist(artist);
            }
            
            if (dtoVideoInsert.getAlbumId() != null) {
                Album album = albumRepository.findById(dtoVideoInsert.getAlbumId())
                        .orElseThrow(() -> new RuntimeException(
                            new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
                video.setAlbum(album);
            }
            
            // genre relation
            if (dtoVideoInsert.getGenreIds() != null && !dtoVideoInsert.getGenreIds().isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(dtoVideoInsert.getGenreIds());
                video.setGenres(genres);
            } else {
                video.setGenres(new ArrayList<>());
            }
            
            Video savedVideo = videoRepository.save(video);
            return videoMapper.toDto(savedVideo);
            
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video: " + e.getMessage());
        }
    }
    
    @Override
    public DtoVideo updateVideo(Long id, DtoVideoUpdate dtoVideoUpdate) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    new ErrorMessage(MessageType.NOT_FOUND, "Video").prepareErrorMessage()));
        
        videoMapper.updateEntityFromDto(dtoVideoUpdate, video);
        
        // update artist
        if (dtoVideoUpdate.getArtistId() != null) {
            Artist artist = artistRepository.findById(dtoVideoUpdate.getArtistId())
                    .orElseThrow(() -> new RuntimeException(
                        new ErrorMessage(MessageType.NOT_FOUND, "Artist").prepareErrorMessage()));
            video.setArtist(artist);
        }
        
        // update album
        if (dtoVideoUpdate.getAlbumId() != null) {
            Album album = albumRepository.findById(dtoVideoUpdate.getAlbumId())
                    .orElseThrow(() -> new RuntimeException(
                        new ErrorMessage(MessageType.NOT_FOUND, "Album").prepareErrorMessage()));
            video.setAlbum(album);
        }
        
        // update genre
        if (dtoVideoUpdate.getGenreIds() != null) {
            if (!dtoVideoUpdate.getGenreIds().isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(dtoVideoUpdate.getGenreIds());
                video.setGenres(genres);
            } else {
                video.setGenres(new ArrayList<>());
            }
        }
        
        return videoMapper.toDto(videoRepository.save(video));
    }
    
    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    new ErrorMessage(MessageType.NOT_FOUND, "Video").prepareErrorMessage()));
        video.setViewCount(video.getViewCount() + 1);
        videoRepository.save(video);
    }

    @Override
    public List<DtoVideo> getVideosByArtist(Long artistId) {
    return videoMapper.toDtoList(videoRepository.findByArtistId(artistId));
    }

    @Override
    public List<DtoVideo> getVideosByAlbum(Long albumId) {
    return videoMapper.toDtoList(videoRepository.findByAlbumId(albumId));
    }
}
