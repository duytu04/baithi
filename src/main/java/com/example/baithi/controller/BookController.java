package com.example.baithi.controller;

import com.example.baithi.dto.BookDto;
import com.example.baithi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * GET /api/books - Get all books with authors and publisher
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    /**
     * GET /api/books/{id} - Get book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return bookService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/books - Create new book
     */
    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto dto) {
        try {
            BookDto created = bookService.create(dto);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/books/{id} - Update book
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto dto) {
        try {
            BookDto updated = bookService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/books/{id} - Delete book
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/books/{bookId}/authors/{authorId} - Add author to book
     */
    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        try {
            bookService.addAuthor(bookId, authorId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/books/{bookId}/authors/{authorId} - Remove author from book
     */
    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        try {
            bookService.removeAuthor(bookId, authorId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
