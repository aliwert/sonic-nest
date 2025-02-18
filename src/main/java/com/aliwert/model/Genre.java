package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
public class Genre extends BaseEntity {
    private String name;
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Track> tracks;
}