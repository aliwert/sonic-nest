package com.aliwert.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audiobooks")
public class Audiobook extends BaseEntity {
    private String title;
    private String author;
    private String narrator;
    private String description;
    private String publisher;
    private Integer duration;
    private String imageUrl;

    @OneToMany(mappedBy = "audiobook")
    private List<Chapter> chapters;
}
