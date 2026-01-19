package com.example.baithi.service;

import com.example.baithi.dto.AuthorDto;
import com.example.baithi.model.Author;
import com.example.baithi.model.AuthorProfile;
import com.example.baithi.model.Book;
import com.example.baithi.repository.AuthorProfileRepository;
import com.example.baithi.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorProfileRepository authorProfileRepository;

    public AuthorService(AuthorRepository authorRepository, AuthorProfileRepository authorProfileRepository) {
        this.authorRepository = authorRepository;
        this.authorProfileRepository = authorProfileRepository;
    }


    public List<AuthorDto> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public Optional<AuthorDto> getById(Long id) {
        return authorRepository.findById(id)
                .map(this::mapToDto);
    }


    public AuthorDto create(AuthorDto dto) {
        // Create Author
        Author author = new Author();
        author.setFullName(dto.getFullName());
        author.setNationality(dto.getNationality());
        Author savedAuthor = authorRepository.save(author);

        // Create Author Profile
        AuthorProfile profile = new AuthorProfile();
        profile.setBiography(dto.getBiography());
        profile.setWebsite(dto.getWebsite());
        profile.setAuthor(savedAuthor);
        authorProfileRepository.save(profile);

        return mapToDto(savedAuthor);
    }


    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        // Update Author
        author.setFullName(dto.getFullName());
        author.setNationality(dto.getNationality());

        // Update Profile
        AuthorProfile profile = author.getAuthorProfile();
        if (profile != null) {
            profile.setBiography(dto.getBiography());
            profile.setWebsite(dto.getWebsite());
        } else {
            // Create profile if it doesn't exist
            profile = new AuthorProfile();
            profile.setBiography(dto.getBiography());
            profile.setWebsite(dto.getWebsite());
            profile.setAuthor(author);
            authorProfileRepository.save(profile);
        }

        return mapToDto(author);
    }


    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        // Check if author has books
        List<Book> books = author.getBooks();
        if (books != null && !books.isEmpty()) {
            throw new RuntimeException("Cannot delete author. Author has " + books.size() + " book(s) associated with them.");
        }

        authorRepository.deleteById(id);
    }


    private AuthorDto mapToDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setFullName(author.getFullName());
        dto.setNationality(author.getNationality());

        // Include profile data if exists
        AuthorProfile profile = author.getAuthorProfile();
        if (profile != null) {
            dto.setBiography(profile.getBiography());
            dto.setWebsite(profile.getWebsite());
        }

        return dto;
    }
}
