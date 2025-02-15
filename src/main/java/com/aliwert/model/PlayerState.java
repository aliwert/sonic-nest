package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_states")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerState extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "current_track_id")
    private Track currentTrack;

    private Integer progressMs;
    private Boolean isPlaying;
    private Boolean shuffleState;
    private String repeatState; // "off", "track", "context"
    private Integer volume;
}