package com.aleksandr.spring.services;

import com.aleksandr.spring.models.Person;
import com.aleksandr.spring.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Person updatedPerson = peopleRepository.findById(id).orElse(null);
        if (updatedPerson != null) {
            updatedPerson.setFullName(person.getFullName());
            updatedPerson.setYearOfBirth(person.getYearOfBirth());
            peopleRepository.save(updatedPerson);
        }
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findPersonByBookId(int bookId) {
        return peopleRepository.findPersonByBookId(bookId);
    }

    public Optional<Person> findPersonByFullName(String fullName) {
        return peopleRepository.findPersonByFullName(fullName);
    }
}
