package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    public List<Book> findByNameContainsIgnoreCase(String name);
    public List<Book> findByNameContainingIgnoreCase(String name);

    @Query(value = "select * from Books a where 1=1", nativeQuery = true)
    List<Book> customFindAllBook();
}
