package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@Table(name = "podcasts")
@NoArgsConstructor
@AllArgsConstructor
public class Podcast extends BaseEntity {
    private String title;
    private String author;
    private String description;
    private String publisher;
    private String language;
    private Boolean explicit;
    private String imageUrl;

    @OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PodcastEpisode> episodes;

    @ManyToMany
    @JoinTable(
            name = "podcast_categories",
            joinColumns = @JoinColumn(name = "podcast_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}