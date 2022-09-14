package com.libraryproject.librarysystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JsonIgnoreProperties("myOrders")
    private Users user;

    @JsonIgnoreProperties("bookOrders")
    @ManyToMany
    private List<Books> booksList;

    private Date issueDate;
    private Date returnDate;
    private OrderStatus orderInfo;

    public Orders() {
    }

    public Orders(Users user, List<Books> booksList, OrderStatus orderInfo) {
        //SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        this.user = user;
        this.booksList = booksList;
        this.issueDate = new Date();
        this.orderInfo = orderInfo;
    }

    public Orders(int orderID, Users user, List<Books> booksList, Date issueDate, Date returnDate, OrderStatus orderInfo) {
        this.orderID = orderID;
        this.user = user;
        this.booksList = booksList;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.orderInfo = orderInfo;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Books> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public OrderStatus getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderStatus orderInfo) {
        this.orderInfo = orderInfo;
    }



}
