package com.aleksandr.spring.repositories;

import com.aleksandr.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Book b JOIN b.owner p WHERE b.bookId=:bookId")
    Optional<Person> findPersonByBookId(@Param("bookId") Integer bookId);

    Optional<Person> findPersonByFullName(String fullName);
}
