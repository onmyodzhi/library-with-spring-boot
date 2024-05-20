package com.aleksandr.spring.controllers;

import com.aleksandr.spring.api.PersonAPI;
import com.aleksandr.spring.models.Person;
import com.aleksandr.spring.services.BooksService;
import com.aleksandr.spring.services.PeopleService;
import com.aleksandr.spring.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/people")
public class PeopleController implements PersonAPI {
    private final PeopleService peopleService;
    private final PersonValidator validator;
    private final BooksService booksService;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator validator, BooksService booksService) {
        this.peopleService = peopleService;
        this.validator = validator;
        this.booksService = booksService;
    }

    @Override
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @Override
    public String show(@PathVariable("person_id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("books", booksService.findBooksByPersonId(peopleService.findById(id)));
        return "people/show";
    }

    @Override
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }


    @Override
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        validator.validate(person, bindingResult);


        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @Override
    public String edit(Model model, @PathVariable("person_id") int id) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @Override
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("person_id") int id) {
        validator.validate(person, bindingResult);


        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @Override
    public String delete(@PathVariable("person_id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
