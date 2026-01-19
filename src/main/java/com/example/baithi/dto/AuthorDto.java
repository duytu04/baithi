package com.example.baithi.dto;

public class AuthorDto {
    private Long id;
    private String fullName;
    private String nationality;
    
    // Author Profile fields
    private String biography;
    private String website;

    // Constructors
    public AuthorDto() {}

    public AuthorDto(Long id, String fullName, String nationality, String biography, String website) {
        this.id = id;
        this.fullName = fullName;
        this.nationality = nationality;
        this.biography = biography;
        this.website = website;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
