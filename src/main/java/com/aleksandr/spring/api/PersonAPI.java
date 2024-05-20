package com.aleksandr.spring.api;

import com.aleksandr.spring.models.Person;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



public interface PersonAPI {
    @GetMapping()
    String index(Model model);

    @GetMapping("/{person_id}")
    String show(@PathVariable("person_id") int id, Model model);

    @GetMapping("/new")
    String newPerson(@ModelAttribute("person") Person person);

    @PostMapping()
    String create(@ModelAttribute("person") @Valid Person person,
                  BindingResult bindingResult);

    @GetMapping("/{person_id}/edit")
    String edit(Model model, @PathVariable("person_id") int id);

    @PatchMapping("/{person_id}")
    String update(@ModelAttribute("person") @Valid Person person,
                  BindingResult bindingResult,
                  @PathVariable("person_id") int id);

    @DeleteMapping("/{person_id}")
    String delete(@PathVariable("person_id") int id);
}
