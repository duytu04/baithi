package com.example.baithi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "authorProfile")
public class AuthorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "website", length = 255)
    private String website;

    //1-1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, unique = true)
    private Author author;


//    Getter / Setter

    public Long getId() {
        return id;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
