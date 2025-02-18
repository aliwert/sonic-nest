package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "chapters")
@NoArgsConstructor
@AllArgsConstructor
public class Chapter extends BaseEntity {
    private String title;
    private Integer duration;
    private Integer chapterNumber;
    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "audiobook_id")
    private Audiobook audiobook;
}