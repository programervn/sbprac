package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersonRepositoryMy {
    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryMy.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> myFindAll() {
        return  null;
    }
}
