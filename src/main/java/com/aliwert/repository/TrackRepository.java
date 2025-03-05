package com.aliwert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliwert.model.Album;
import com.aliwert.model.Category;
import com.aliwert.model.Genre;
import com.aliwert.model.Track;

@Repository
public interface TrackRepository extends JpaRepository <Track, Long> {
    List<Track> findByAlbum(Album album);

    List<Track> findByAlbumId(Long albumId);

    List<Track> findByGenresContaining(Genre genre);

    List<Track> findByCategoriesContaining(Category category);
    
}
