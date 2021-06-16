package com.thaipd.sbprac.service.impl;

import com.thaipd.sbprac.entity.Person;
import com.thaipd.sbprac.repository.PersonRepository;
import com.thaipd.sbprac.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }
}
