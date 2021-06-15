package com.thaipd.sbprac.controller;

import com.thaipd.sbprac.entity.Book;
import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.repository.BookRepository;
import com.thaipd.sbprac.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
for rapid testing, controller call repository instead of service
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @GetMapping({"", "/"})
    public List<Book> findAllPlayer() {
        logger.info("Get all book");
        return bookRepository.findAll();
    }

    // Search
    @GetMapping("/search")
    //(name = "id")
    List<Book> searchBook(@RequestParam String name) {
        logger.info("Search book with name: {}", name);
        List<Book> bookList;

        bookList = bookRepository.findByNameContainsIgnoreCase(name);
        logger.debug("List by Contains");
        for (Book b : bookList) {
            logger.debug("{}", b);
        }

        bookList = bookRepository.findByNameContainingIgnoreCase(name);
        logger.debug("List by Containing");
        for (Book b : bookList) {
            logger.debug("{}", b);
        }
        return  bookList;
    }

    // Find
    @GetMapping("/{id}")
    Book findOne(@PathVariable Long id) {
        logger.info("Get one book");
        return bookRepository.findById(id).orElse(null);
    }

    // Save
    @PostMapping("")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    // Save or update
    @PutMapping("/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        return bookRepository.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setPrice(newBook.getPrice());
                    return bookRepository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
    }
}
