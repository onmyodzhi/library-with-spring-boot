package com.aleksandr.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;


import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "full_name")
    @NotEmpty(message = "This field should not empty")
    @NotNull(message = "This field should not empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "[A-Z][- A-Za-z]+", message = "Full name must begin with a capital letter and can only contain letters, hyphens, and spaces")
    private String fullName;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "age of year should be more then 1900")
    @NotNull(message = "field year of birth should not be empty")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String FullName, int ageOfYear) {
        this.fullName = FullName;
        this.yearOfBirth = ageOfYear;
    }

    @Override
    public String toString() {
        return
                "person id = " + personId +
                ", Full name = '" + fullName + '\'' +
                ", year of birth = " + yearOfBirth;
    }
}
