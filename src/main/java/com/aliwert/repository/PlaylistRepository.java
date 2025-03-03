package com.aliwert.repository;
import com.aliwert.model.Playlist;
import com.aliwert.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUser(User user);
    List<Playlist> findByUserId(Long userId);
    List<Playlist> findByIsPublicTrue();
}
