package com.aliwert.repository;

import com.aliwert.model.Audiobook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends JpaRepository<Audiobook, Long> {
}