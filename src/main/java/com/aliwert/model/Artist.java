package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "artists")
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends BaseEntity {

    private String name;

    private String biography;

    private String imageUrl;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;
}
