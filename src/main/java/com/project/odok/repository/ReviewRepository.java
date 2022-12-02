package com.project.odok.repository;

import com.project.odok.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByOrderByCreatedAtDesc();

}
