package com.aleksandr.spring.services;


import com.aleksandr.spring.models.Book;
import com.aleksandr.spring.models.Person;
import com.aleksandr.spring.repositories.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BooksService {
    private final BooksRepository booksRepository;

    public List<Book> findAll() {
        List<Book> sortedBooks = booksRepository.findAll();
        Collections.sort(sortedBooks);
        return sortedBooks;
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Book book) {
        Book updatedBook = booksRepository.findById(id).orElse(null);
        if (updatedBook != null) {
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());

            Person owner = book.getOwner();
            if (owner != null) {
                updatedBook.setOwner(owner);
            }
            updatedBook.setAgeOfRelease(book.getAgeOfRelease());
            booksRepository.save(updatedBook);
        }
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }



    @Transactional
    public void release(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setDateOfAssign(null);
            book.setOwner(null);
        }
    }

    @Transactional
    public void assignPersonToBook(int bookId, Person person) {
        Book book = findById(bookId);
        book.setOwner(person);
        book.setDateOfAssign(LocalDate.now());
    }

    public boolean checkOverdue(int bookId) {
        Book book = findById(bookId);
        if (book.getDateOfAssign() != null) {
            return book.getDateOfAssign().plusDays(10).isBefore(LocalDate.now());
        }
        return false;
    }

    public List<Book> findAllByTitleStartingWith(String title) {
        return booksRepository.findAllByTitleStartingWith(title);
    }

    public List<Book> findBooksByPersonId(Person person) {
        return booksRepository.findBooksByPersonId(person);
    }

    public List<Book> getPageOfBooks(int page, int booksPerPage) {
        int start = (page - 1) * booksPerPage;
        int end = start + booksPerPage;
        List<Book> pageOfBooks = findAll();

        if (start >= pageOfBooks.size()) {
            return null;
        }
        if (end > pageOfBooks.size()) {
            end = pageOfBooks.size();
        }

        return pageOfBooks.subList(start, end);
    }
}
