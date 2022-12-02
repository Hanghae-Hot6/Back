package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    List<Review> findAllByOrderByCreatedAtDesc(Club club);
    List<Review> findAllByClubOrderByCreatedAtDesc(Club club);

}
