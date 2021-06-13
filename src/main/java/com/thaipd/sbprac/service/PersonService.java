package com.thaipd.sbprac.service;

import com.thaipd.sbprac.entity.Person;

import java.util.List;

public interface PersonService {
    public Person create(Person person);
    public List<Person> findAll();
}
