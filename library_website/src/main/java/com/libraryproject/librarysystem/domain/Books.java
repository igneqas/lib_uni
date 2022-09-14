package com.libraryproject.librarysystem.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToMany(mappedBy = "booksList", cascade = {CascadeType.MERGE})
    @OrderBy("authorID ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Authors> authorsList;

    @Enumerated
    private Genre genre;

    private String isbn;

    private String releaseYear;

    private String description;

    private String url;

    @Enumerated
    private Availability availability;

    @ManyToMany(mappedBy = "booksList", cascade = {CascadeType.MERGE})
    @OrderBy("orderID ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Orders> bookOrders;

    public Books() {
    }

    public Books(int id, String title, List<Authors> authorsList, Genre genre, String isbn, String releaseYear, String description, String url, Availability availability, List<Orders> bookOrders) {
        this.id = id;
        this.title = title;
        this.authorsList = authorsList;
        this.genre = genre;
        this.isbn = isbn;
        this.releaseYear = releaseYear;
        this.description = description;
        this.url = url;
        this.availability = availability;
        this.bookOrders = bookOrders;
    }

    public Books(String title, List<Authors> authorsList, Genre genre, String isbn, String releaseYear, String description, String url, Availability availability, List<Orders> bookOrders) {
        this.title = title;
        this.authorsList = authorsList;
        this.genre = genre;
        this.isbn = isbn;
        this.releaseYear = releaseYear;
        this.description = description;
        this.url = url;
        this.availability = availability;
        this.bookOrders = bookOrders;
    }

    public Books(String title, Availability availability) {
        this.title = title;
        this.availability = availability;
    }

    public Books(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Books(String title, Genre genre, String isbn, String releaseYear, String description, String url) {
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.releaseYear = releaseYear;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Authors> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Authors> authorsList) {
        this.authorsList = authorsList;
    }

    public List<Orders> getBookOrders() {
        return bookOrders;
    }

    public void setBookOrders(List<Orders> bookOrders) {
        this.bookOrders = bookOrders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorsList=" + authorsList +
                ", url='" + url + '\'' +
                ", availability=" + availability +
                ", bookOrders=" + bookOrders +
                '}';
    }
}
