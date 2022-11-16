package com.project.odok.repository;

import com.project.odok.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    Book findByIsbn(String isbn);
}
