package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    private String name;
    private String description;
    private String imageUrl;

    @ManyToMany(mappedBy = "categories")
    private List<Track> tracks;
}