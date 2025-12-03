package com.example.tutoring.repository;

import com.example.tutoring.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCreatedBy(Long userId);
    List<Document> findByFileNameContaining(String keyword);
}