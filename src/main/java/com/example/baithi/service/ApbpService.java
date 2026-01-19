package com.example.baithi.service;

import com.example.baithi.dto.Apbp;
import com.example.baithi.model.*;
import com.example.baithi.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApbpService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorProfileRepository authorProfileRepository;
    private final PublisherRepository publisherRepository;

    public ApbpService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            AuthorProfileRepository authorProfileRepository,
            PublisherRepository publisherRepository
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorProfileRepository = authorProfileRepository;
        this.publisherRepository = publisherRepository;
    }


    public Apbp create(Apbp dto) {

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        Author author = new Author();
        author.setFullName(dto.getAuthorName());
        author.setNationality(dto.getNationality());
        authorRepository.save(author);

        AuthorProfile profile = new AuthorProfile();
        profile.setBiography(dto.getBiography());
        profile.setWebsite(dto.getWebsite());
        profile.setAuthor(author);
        authorProfileRepository.save(profile);

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPages(dto.getPages());
        book.setPublisher(publisher);
        book.getAuthors().add(author);

        bookRepository.save(book);

        return mapToDto(book);
    }


    public Optional<Apbp> getByBookId(Long bookId) {
        return bookRepository.findById(bookId).map(this::mapToDto);
    }


    public List<Apbp> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public Apbp update(Long bookId, Apbp dto) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPages(dto.getPages());

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        book.setPublisher(publisher);

        Author author = book.getAuthors().get(0);
        author.setFullName(dto.getAuthorName());
        author.setNationality(dto.getNationality());

        AuthorProfile profile = author.getAuthorProfile();
        profile.setBiography(dto.getBiography());
        profile.setWebsite(dto.getWebsite());

        return mapToDto(book);
    }


    public void delete(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    /* ===== MAPPER ===== */
    private Apbp mapToDto(Book book) {
        Apbp dto = new Apbp();

        dto.setBookId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setPages(book.getPages());

        Publisher publisher = book.getPublisher();
        dto.setPublisherId(publisher.getId());
        dto.setPublisherName(publisher.getName());

        Author author = book.getAuthors().get(0);
        dto.setAuthorId(author.getId());
        dto.setAuthorName(author.getFullName());
        dto.setNationality(author.getNationality());

        AuthorProfile profile = author.getAuthorProfile();
        if (profile != null) {
            dto.setBiography(profile.getBiography());
            dto.setWebsite(profile.getWebsite());
        }

        return dto;
    }
}
