package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@AllArgsConstructor
public class Album extends BaseEntity {
    private String title;
    private LocalDate releaseDate;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Track> tracks;

    @ManyToMany
    @JoinTable(
        name = "album_genres",
        joinColumns = @JoinColumn(name = "album_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}