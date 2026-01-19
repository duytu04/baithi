package com.example.baithi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "author")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;


    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "nationality", length = 100)
    private String nationality;

    @OneToOne(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AuthorProfile authorProfile;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;



    //Getter / Setter

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public AuthorProfile getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfile authorProfile) {
        this.authorProfile = authorProfile;
    }

    public List<Book> getBooks() {
        return books;
    }



}
