package com.thaipd.sbprac.service.impl;

import com.thaipd.sbprac.model.Book;
import com.thaipd.sbprac.repository.BookRepository;
import com.thaipd.sbprac.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findByIdPkg(id);
    }

    public List<Book> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    public List<Book> findBookByName2(String name) {
        return bookRepository.findBookByName2(name);
    }

    public List<Book> findBookByName3(String name) {
        return bookRepository.findBookByName3(name);
    }

    public BigDecimal findBookPrice(Long id) {
        return bookRepository.findBookPrice(id);
    }
}
