package com.loanapp.service;

import java.nio.file.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.loanapp.dto.DocumentRequestDto;
import com.loanapp.dto.DocumentResponseDto;
import com.loanapp.entity.Document;
import com.loanapp.entity.User;
import com.loanapp.enums.DocumentStatus;
import com.loanapp.mapper.DocumentMapper;
import com.loanapp.repository.DocumentRepository;
import com.loanapp.repository.UserRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentMapper mapper;

    // ============================
    // 📤 UPLOAD DOCUMENT
    // ============================
    @Override
    public String uploadDocument(MultipartFile file, DocumentRequestDto dto) {

        try {
            // 🔥 VALIDATION
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            if (!file.getContentType().equals("application/pdf") &&
                !file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Only PDF or Image files allowed");
            }

            // 📁 CREATE FOLDER
            Path uploadPath = Paths.get("uploads").toAbsolutePath();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 🔥 UNIQUE FILE NAME
            String fileName = System.currentTimeMillis() + "_" +
                    file.getOriginalFilename().replaceAll(" ", "_");

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 🔐 GET ACTIVE USER
            User user = userRepository.findActiveUserById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 💾 SAVE DOCUMENT
            Document doc = new Document();
            doc.setDocumentType(dto.getDocumentType());
            doc.setFileUrl(filePath.toString());
            doc.setUser(user);

            documentRepository.save(doc);

            return "Document uploaded successfully";

        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // ============================
    // 📄 GET PENDING DOCUMENTS
    // ============================
    @Override
    public List<DocumentResponseDto> getPendingDocuments() {
        return documentRepository.findByStatus(DocumentStatus.PENDING)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // ============================
    // ✅ APPROVE DOCUMENT
    // ============================
    @Override
    public String approveDocument(Long id) {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!doc.getStatus().equals(DocumentStatus.PENDING)) {
            throw new RuntimeException("Document already processed");
        }

        doc.setStatus(DocumentStatus.APPROVED);

        documentRepository.save(doc);

        return "Document approved";
    }

    // ============================
    // ❌ REJECT DOCUMENT
    // ============================
    @Override
    public String rejectDocument(Long id, String remark) {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!doc.getStatus().equals(DocumentStatus.PENDING)) {
            throw new RuntimeException("Document already processed");
        }

        doc.setStatus(DocumentStatus.REJECTED);
        doc.setRemarks(remark);

        documentRepository.save(doc);

        return "Document rejected";
    }
}