package com.example.baithi.repository;

import com.example.baithi.model.Book;
import com.example.baithi.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);
    List<Book> findByPublisher_Id(Long publisherId);
    List<Book> findByPublisher(Publisher publisher);
}
