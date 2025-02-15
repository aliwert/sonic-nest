package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
public class Show extends BaseEntity {
    private String title;
    private String description;
    private String publisher;
    private String imageUrl;

    @OneToMany(mappedBy = "show")
    private List<Episode> episodes;
}