package com.example.baithi.repository;

import com.example.baithi.model.AuthorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, Long> {

    Optional<AuthorProfile> findByAuthor_Id(Long authorId);
}
