package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "episodes")
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends BaseEntity {
    private String title;
    private String description;
    private Integer duration; // in seconds
    private String audioUrl;
    private LocalDateTime releaseDate;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}