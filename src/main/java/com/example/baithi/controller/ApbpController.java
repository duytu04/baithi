package com.example.baithi.controller;

import com.example.baithi.dto.Apbp;
import com.example.baithi.service.ApbpService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apbp")
public class ApbpController {

    private final ApbpService apbpService;

    public ApbpController(ApbpService apbpService) {
        this.apbpService = apbpService;
    }


    @PostMapping
    public ResponseEntity<Apbp> create(@RequestBody Apbp dto) {
        return ResponseEntity.ok(apbpService.create(dto));
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<Apbp> getByBookId(@PathVariable Long bookId) {
        return apbpService.getByBookId(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<Apbp>> getAll() {
        return ResponseEntity.ok(apbpService.getAll());
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<Apbp> update(
            @PathVariable Long bookId,
            @RequestBody Apbp dto
    ) {
        return ResponseEntity.ok(apbpService.update(bookId, dto));
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookId) {
        apbpService.delete(bookId);
        return ResponseEntity.noContent().build();
    }
}
