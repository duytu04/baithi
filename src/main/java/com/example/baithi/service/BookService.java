package com.example.baithi.service;

import com.example.baithi.dto.BookDto;
import com.example.baithi.model.Author;
import com.example.baithi.model.Book;
import com.example.baithi.model.Publisher;
import com.example.baithi.repository.AuthorRepository;
import com.example.baithi.repository.BookRepository;
import com.example.baithi.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Get all books with authors and publisher
     */
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get book by ID
     */
    public Optional<BookDto> getById(Long id) {
        return bookRepository.findById(id)
                .map(this::mapToDto);
    }

    /**
     * Create new book
     */
    public BookDto create(BookDto dto) {
        // Find publisher
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + dto.getPublisherId()));

        // Create book
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPages(dto.getPages());
        book.setPublisher(publisher);

        // Add authors if provided
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
            if (authors.size() != dto.getAuthorIds().size()) {
                throw new RuntimeException("One or more author IDs not found");
            }
            book.setAuthors(authors);
        }

        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    /**
     * Update book
     */
    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update basic fields
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPages(dto.getPages());

        // Update publisher if changed
        if (!book.getPublisher().getId().equals(dto.getPublisherId())) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + dto.getPublisherId()));
            book.setPublisher(publisher);
        }

        // Update authors if provided
        if (dto.getAuthorIds() != null) {
            List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
            if (authors.size() != dto.getAuthorIds().size()) {
                throw new RuntimeException("One or more author IDs not found");
            }
            book.setAuthors(authors);
        }

        return mapToDto(book);
    }

    /**
     * Delete book
     */
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Add author to book
     */
    public void addAuthor(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
        }
    }

    /**
     * Remove author from book
     */
    public void removeAuthor(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        book.getAuthors().remove(author);
    }

    /**
     * Map Entity to DTO
     */
    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setPages(book.getPages());

        // Publisher info
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getId());
            dto.setPublisherName(book.getPublisher().getName());
        }

        // Authors info
        if (book.getAuthors() != null) {
            List<Long> authorIds = book.getAuthors().stream()
                    .map(Author::getId)
                    .collect(Collectors.toList());
            List<String> authorNames = book.getAuthors().stream()
                    .map(Author::getFullName)
                    .collect(Collectors.toList());
            dto.setAuthorIds(authorIds);
            dto.setAuthorNames(authorNames);
        }

        return dto;
    }
}
