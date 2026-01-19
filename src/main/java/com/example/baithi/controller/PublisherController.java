package com.example.baithi.controller;

import com.example.baithi.dto.PublisherDto;
import com.example.baithi.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }


    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAll() {
        return ResponseEntity.ok(publisherService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getById(@PathVariable Long id) {
        return publisherService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody PublisherDto dto) {
        try {
            PublisherDto created = publisherService.create(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            // Return error message to help debug the issue
            String errorMessage = "Error creating publisher: " + e.getMessage();
            if (e.getCause() != null) {
                errorMessage += " | Cause: " + e.getCause().getMessage();
            }
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> update(@PathVariable Long id, @RequestBody PublisherDto dto) {
        try {
            PublisherDto updated = publisherService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            publisherService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Return error message if publisher has books
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
