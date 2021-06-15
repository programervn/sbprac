package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
Book repo ngoài kế thừa JpaRepo còn được kế thừa repo custom để viết các hàm tùy ý
 */
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    public List<Book> findByNameContainsIgnoreCase(String name);
    public List<Book> findByNameContainingIgnoreCase(String name);

    //@Query(value = "select a.id, a.name, a.price, a.author from Books a where 1=1", nativeQuery = true)
    //Lấy thừa trường thì OK, lấy thiếu trường so với entity sẽ lỗi
    @Query(value = "select a.id, a.name, a.price, a.author from Books a where 1=1", nativeQuery = true)
    List<Book> customFindAllBook();

    //Get only selected column, must to map column manual
    @Query(value = "select a.id, a.name from Books a where 1=1", nativeQuery = true)
    List<Object[]> customFindSelectedColumn();
}
