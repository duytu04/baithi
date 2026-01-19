package com.example.baithi.dto;

import java.util.ArrayList;
import java.util.List;

public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private Integer publishedYear;
    private Integer pages;
    
    // Relations
    private Long publisherId;
    private String publisherName;
    private List<Long> authorIds = new ArrayList<>();
    private List<String> authorNames = new ArrayList<>();

    // Constructors
    public BookDto() {}

    public BookDto(Long id, String title, String isbn, Integer publishedYear, Integer pages, 
                   Long publisherId, String publisherName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.pages = pages;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }
}
