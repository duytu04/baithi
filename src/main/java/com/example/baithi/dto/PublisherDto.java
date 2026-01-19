package com.example.baithi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PublisherDto {
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;
    
    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;
    
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
    
    @Size(max = 50, message = "Phone must not exceed 50 characters")
    private String phone;

    // Constructors
    public PublisherDto() {}

    public PublisherDto(Long id, String name, String address, String website, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
