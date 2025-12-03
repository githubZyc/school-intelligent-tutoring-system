package com.example.tutoring.service;

import com.example.tutoring.entity.Document;
import com.example.tutoring.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(String fileName, String fileType, String content, Long userId) {
        Document document = new Document();
        document.setFileName(fileName);
        document.setFileType(fileType);
        document.setContent(content);
        document.setCreatedBy(userId);
        return documentRepository.save(document);
    }

    public Document saveDocument(MultipartFile file, Long userId) throws IOException {
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setContent(new String(file.getBytes()));
        document.setCreatedBy(userId);
        return documentRepository.save(document);
    }

    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    public List<Document> findByUserId(Long userId) {
        return documentRepository.findByCreatedBy(userId);
    }

    public List<Document> searchDocuments(String keyword) {
        return documentRepository.findByFileNameContaining(keyword);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}