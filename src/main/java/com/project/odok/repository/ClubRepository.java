package com.project.odok.repository;

import com.project.odok.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAllByOrderByCreatedAtDesc();
}
