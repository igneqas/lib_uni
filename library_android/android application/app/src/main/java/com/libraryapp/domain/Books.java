package com.libraryapp.domain;


import java.util.List;

public class Books {

    private int id;

    private String title;
    private List<Authors> authorsList;

    private String url;
    private Availability availability;

    private List<Orders> bookOrders;
    private String authors;

    public Books() {
    }

    public Books(int id, String title, List<Authors> authorsList, String url, Availability availability, List<Orders> bookOrders) {
        this.id = id;
        this.title = title;
        this.authorsList = authorsList;
        this.url = url;
        this.availability = availability;
        this.bookOrders = bookOrders;
    }

    public Books(int id, String title, String authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public Books(String title, Availability availability) {
        this.title = title;
        this.availability = availability;
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

    @Override
    public String toString() {
        return  id + " : " + title + authors;
    }
}
