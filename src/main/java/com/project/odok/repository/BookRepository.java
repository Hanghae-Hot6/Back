package com.project.odok.repository;

import com.project.odok.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByIdContaining(String keyword);
}
