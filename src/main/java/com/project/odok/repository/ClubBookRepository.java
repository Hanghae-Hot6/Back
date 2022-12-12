package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.ClubBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBookRepository extends JpaRepository<ClubBook, Long> {
    ClubBook findByClub(Club club);
}
