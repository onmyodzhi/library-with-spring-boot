package com.aleksandr.spring.api;

import com.aleksandr.spring.models.Book;
import com.aleksandr.spring.models.Person;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


public interface BookAPI {
    @GetMapping
    String index(Model model,
                 @RequestParam(name = "page", required = false) Integer page,
                 @RequestParam(name = "books_per_page", required = false) Integer booksPerPage);

    @GetMapping("/{id}")
    String show(@PathVariable("id") int bookId, Model model,
                @ModelAttribute("person") Person person);

    @GetMapping("/new")
    String newBook(Model model);

    @PostMapping
    String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult);

    @GetMapping("/{book_id}/edit")
    String edit(Model model, @PathVariable("book_id") int bookId);

    @PatchMapping("/{book_id}")
    String update(@PathVariable("book_id") int bookId,
                  @ModelAttribute("book") @Valid Book book,
                  BindingResult bindingResult);

    @DeleteMapping("/{book_id}")
    String delete(@PathVariable("book_id") int bookId);

    @PatchMapping("/{id}/release")
    String release(@PathVariable("id") int bookId);

    @PatchMapping("/{id}/assign")
    String assign(@PathVariable("id") int id,
                  @ModelAttribute("person") Person selectedPerson);

    @GetMapping("/search")
    String search(Model model,
                  @RequestParam(name = "titleLike", required = false) String title);
}
