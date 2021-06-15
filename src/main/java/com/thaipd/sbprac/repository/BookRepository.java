package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    public List<Book> findByNameContainsIgnoreCase(String name);
    public List<Book> findByNameContainingIgnoreCase(String name);
}
