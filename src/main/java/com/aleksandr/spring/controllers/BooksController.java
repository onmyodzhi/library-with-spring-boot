package com.aleksandr.spring.controllers;

import com.aleksandr.spring.api.BookAPI;
import com.aleksandr.spring.models.Book;
import com.aleksandr.spring.models.Person;
import com.aleksandr.spring.services.BooksService;
import com.aleksandr.spring.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController implements BookAPI {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @Override
    public String index(Model model,
                        @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage) {
        if ((page == null || page < 1) || (booksPerPage == null || booksPerPage < 1)) {
            model.addAttribute("books", booksService.findAll());
        return "books/index";
        }
        List<Book> books = booksService.getPageOfBooks(page, booksPerPage);
        model.addAttribute("books", books);
        return "books/index";
    }


    @Override
    public String show(@PathVariable("id") int bookId, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findById(bookId));

        Optional<Person> bookOwner = peopleService.findPersonByBookId(bookId);

        boolean isOverdue = booksService.checkOverdue(bookId);
        model.addAttribute("overdue", isOverdue);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @Override
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @Override
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @Override
    public String edit(Model model, @PathVariable("book_id") int bookId) {
        model.addAttribute("book", booksService.findById(bookId));
        return "books/edit";
    }

    @Override
    public String update(@PathVariable("book_id") int bookId,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(bookId, book);
        return "redirect:/books";
    }

    @Override
    public String delete(@PathVariable("book_id") int bookId) {
        booksService.delete(bookId);
        return "redirect:/books";
    }

    @Override
    public String release(@PathVariable("id") int bookId) {
        booksService.release(bookId);
        return "redirect:/books/" + bookId;
    }

    @Override
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person selectedPerson) {
        booksService.assignPersonToBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @Override
    public String search(Model model,
                         @RequestParam(name = "titleLike", required = false) String title) {
        List<Book> books = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            books = booksService.findAllByTitleStartingWith(title);
            for (Book book : books) {
                Person owner = book.getOwner();

                if (owner != null) {
                    model.addAttribute("owner_" + book.getBookId(), owner);
                }
            }
        }
        model.addAttribute("titleLike", title);
        model.addAttribute("books", books);
        return "books/search";
    }
}
