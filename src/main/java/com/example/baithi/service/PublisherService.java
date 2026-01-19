package com.example.baithi.service;

import com.example.baithi.dto.PublisherDto;
import com.example.baithi.model.Book;
import com.example.baithi.model.Publisher;
import com.example.baithi.repository.BookRepository;
import com.example.baithi.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all publishers
     */
    public List<PublisherDto> getAll() {
        return publisherRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get publisher by ID
     */
    public Optional<PublisherDto> getById(Long id) {
        return publisherRepository.findById(id)
                .map(this::mapToDto);
    }

    /**
     * Create new publisher
     */
    public PublisherDto create(PublisherDto dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setWebsite(dto.getWebsite());
        publisher.setEmail(dto.getEmail());
        publisher.setPhone(dto.getPhone());

        Publisher saved = publisherRepository.save(publisher);
        return mapToDto(saved);
    }

    /**
     * Update publisher
     */
    public PublisherDto update(Long id, PublisherDto dto) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));

        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setWebsite(dto.getWebsite());
        publisher.setEmail(dto.getEmail());
        publisher.setPhone(dto.getPhone());

        return mapToDto(publisher);
    }

    /**
     * Delete publisher
     * Prevents deletion if publisher has books
     */
    public void delete(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));

        // Check if publisher has books
        List<Book> books = bookRepository.findByPublisher(publisher);
        if (!books.isEmpty()) {
            throw new RuntimeException("Cannot delete publisher. It has " + books.size() + " book(s) associated with it.");
        }

        publisherRepository.deleteById(id);
    }

    /**
     * Map Entity to DTO
     */
    private PublisherDto mapToDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setWebsite(publisher.getWebsite());
        dto.setEmail(publisher.getEmail());
        dto.setPhone(publisher.getPhone());
        return dto;
    }
}
