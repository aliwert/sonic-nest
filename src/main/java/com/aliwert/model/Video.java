package com.aliwert.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "videos")
@NoArgsConstructor
@AllArgsConstructor
public class Video extends BaseEntity {
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer duration; // second 
    private Long viewCount = 0L;
    private LocalDate uploadDate;
    private String videoType; // MUSIC_VIDEO, CONCERT, INTERVIEW etc 
    
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album; // optional for albums
    
    @ManyToMany
    @JoinTable(
        name = "video_genres",
        joinColumns = @JoinColumn(name = "video_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}