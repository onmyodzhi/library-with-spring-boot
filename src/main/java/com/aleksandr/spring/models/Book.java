package com.aleksandr.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;


import java.time.LocalDate;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book implements Comparable<Book>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title")
    @NotEmpty(message = "field title should not be empty")
    @Size(max = 100)
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "field author should not be empty")
    @Pattern(regexp = "[A-Z][- A-Za-z]+", message = "The author must begin with a capital letter and can only contain letters, hyphens, and spaces")
    private String author;

    @Column(name = "age_of_release")
    @NotNull(message = "Field age of release should not be empty")
    @Min(value = -2000, message = "must be greater than or equal to -2000")
    @Max(value = 2024, message = "must be less than or equal to 2024")
    private int ageOfRelease;

    @Column(name = "date_of_assign")
    private LocalDate dateOfAssign;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book(String title, String author, int ageOfRelease) {
        this.title = title;
        this.author = author;
        this.ageOfRelease = ageOfRelease;
    }

    @Override
    public int compareTo(Book otherBook) {
        return this.ageOfRelease - otherBook.ageOfRelease;
    }

    @Override
    public String toString() {
        return
                "book id = " + bookId +
                ", title = '" + title + '\'' +
                ", author = '" + author + '\'' +
                ", age of release = '" + ageOfRelease + '\'';
    }
}
