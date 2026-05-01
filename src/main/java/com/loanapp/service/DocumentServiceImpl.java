package com.loanapp.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import java.io.IOException;

@Service
public class DocumentServiceImpl implements DocumentService {
	@Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentMapper mapper;
   
    @Override
    public String uploadDocument(MultipartFile file, DocumentRequestDto dto) {

        try {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename().replaceAll(" ", "_");
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Document doc = new Document();
            doc.setDocumentType(dto.getDocumentType());
            doc.setFileUrl(filePath.toString());
            doc.setUser(user);

            // 🔥 ADD HERE
            System.out.println("Before save");

            doc = documentRepository.save(doc);

            System.out.println("After save");
            System.out.println("Saved ID: " + doc.getId());

            return "Document uploaded successfully";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public List<DocumentResponseDto> getPendingDocuments() {
        return documentRepository.findByStatus(DocumentStatus.PENDING)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public String approveDocument(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        doc.setStatus(DocumentStatus.APPROVED);
        documentRepository.save(doc);

        return "Document approved";
    }

    @Override
    public String rejectDocument(Long id, String remark) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        doc.setStatus(DocumentStatus.REJECTED);
        doc.setRemarks(remark);

        documentRepository.save(doc);

        return "Document rejected";
    }
	
}
