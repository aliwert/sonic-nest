package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "podcast_episodes")
@NoArgsConstructor
@AllArgsConstructor
public class PodcastEpisode extends BaseEntity {
    private String title;
    private String description;
    private Integer duration;
    private String audioUrl;
    private LocalDateTime releaseDate;
    private String imageUrl;
    private Integer episodeNumber;
    private Boolean explicit;

    @ManyToOne
    @JoinColumn(name = "podcast_id")
    private Podcast podcast;
}