package com.aliwert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aliwert.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByArtistId(Long artistId);
    List<Video> findByAlbumId(Long albumId);
    List<Video> findByGenresId(Long genreId);
    List<Video> findByTitleContainingIgnoreCase(String title);
    List<Video> findByOrderByViewCountDesc();
    List<Video> findByOrderByUploadDateDesc();
}
