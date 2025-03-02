package com.aliwert.repository;

import com.aliwert.model.PlayerState;
import com.aliwert.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerStateRepository extends JpaRepository<PlayerState, Long> {
    Optional<PlayerState> findByUser(User user);
    Optional<PlayerState> findByUserId(Long userId);
}