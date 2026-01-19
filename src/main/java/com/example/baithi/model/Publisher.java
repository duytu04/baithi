package com.example.baithi.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table (name = "publisher", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;


    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }
}
