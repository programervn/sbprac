package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    /*
    result set must have all column, or exception will be thrown
     */
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("ID"));
        book.setName(rs.getString("name"));
        book.setPrice(rs.getBigDecimal("price"));
        return book;
    }
}
