package com.thaipd.sbprac.service;

import com.thaipd.sbprac.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    public Optional<Book> findById(Long id);
    public List<Book> findBookByName(String name);
    public List<Book> findBookByName2(String name);
    public List<Book> findBookByName3(String name);
    public BigDecimal findBookPrice(Long id);
}
