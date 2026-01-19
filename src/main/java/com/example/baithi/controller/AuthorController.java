package com.example.baithi.controller;

import com.example.baithi.dto.AuthorDto;
import com.example.baithi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    /**
     * GET /api/authors/{id} - Get author by ID with profile
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return authorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/authors - Create new author with profile
     */
    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto dto) {
        try {
            AuthorDto created = authorService.create(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/authors/{id} - Update author and profile
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody AuthorDto dto) {
        try {
            AuthorDto updated = authorService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/authors/{id} - Delete author
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            authorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Return error message if author has books
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
