// DocumentServiceImpl.java (FULL UPDATED VERSION)

package com.loanapp.service;

import java.nio.file.*;
import java.util.List;

import com.loanapp.exception.DocumentNotFoundException;
import com.loanapp.exception.LoanNotFoundException;
import com.loanapp.exception.UnauthorizedAccessException;
import com.loanapp.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.loanapp.dto.DocumentRequestDto;
import com.loanapp.dto.DocumentResponseDto;
import com.loanapp.entity.Document;
import com.loanapp.entity.Loan;
import com.loanapp.entity.User;
import com.loanapp.enums.DocumentStatus;
import com.loanapp.mapper.DocumentMapper;
import com.loanapp.repository.DocumentRepository;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.UserRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentMapper mapper;

    @Autowired
    private CreditScoreService creditScoreService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EmailService emailService;

    // ====================================
    // UPLOAD DOCUMENT
    // ====================================

    @Override
    @Transactional
    public String uploadDocument(MultipartFile file,
                                 DocumentRequestDto dto) {

        try {

            // ====================================
            // FILE VALIDATION
            // ====================================

            if (file.isEmpty()) {

                throw new RuntimeException(
                        "File is empty"
                );
            }

            String contentType =
                    file.getContentType();

            if (contentType == null ||
                    (!contentType.equals("application/pdf")
                            &&
                     !contentType.startsWith("image/"))) {

                throw new RuntimeException(
                        "Only PDF or image files allowed"
                );
            }

            // ====================================
            // SANITIZE FILE NAME
            // ====================================

            String cleanFileName =
                    StringUtils.cleanPath(
                            file.getOriginalFilename()
                    );

            // ====================================
            // USER VALIDATION
            // ====================================

            User user = userRepository
                    .findActiveUserById(dto.getUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "User not found"
                            ));

            // ====================================
            // LOAN VALIDATION
            // ====================================

            Loan loan = loanRepository.findById(
                            dto.getLoanId()
                    )
                    .orElseThrow(() ->
                            new LoanNotFoundException(
                                    "Loan not found"
                            ));

            // ====================================
            // SECURITY CHECK
            // ====================================

            if (!loan.getUser()
                    .getId()
                    .equals(user.getId())) {

                throw new UnauthorizedAccessException(
                        "Unauthorized loan access"
                );
            }

            // ====================================
            // CREATE UPLOAD DIRECTORY
            // ====================================

            Path uploadPath =
                    Paths.get("uploads")
                            .toAbsolutePath();

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            // ====================================
            // UNIQUE FILE NAME
            // ====================================

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + cleanFileName.replaceAll(
                                    " ",
                                    "_"
                            );

            Path filePath =
                    uploadPath.resolve(fileName);

            // ====================================
            // SAVE FILE
            // ====================================

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // ====================================
            // SAVE DOCUMENT
            // ====================================

            Document doc = new Document();

            doc.setDocumentType(
                    dto.getDocumentType()
            );

            doc.setFileUrl(filePath.toString());

            doc.setUser(user);

            doc.setLoan(loan);

            documentRepository.save(doc);

            return "Document uploaded successfully";

        } catch (Exception e) {

            throw new RuntimeException(
                    "File upload failed: "
                            + e.getMessage()
            );
        }
    }

    // ====================================
    // GET PENDING DOCUMENTS
    // ====================================

    @Override
    public List<DocumentResponseDto>
    getPendingDocuments() {

        return documentRepository.findByStatus(
                        DocumentStatus.PENDING
                )
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // ====================================
    // APPROVE DOCUMENT
    // ====================================

    @Override
    @Transactional
    public String approveDocument(Long id) {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() ->
                        new DocumentNotFoundException(
                                "Document not found"
                        ));

        if (!doc.getStatus().equals(
                DocumentStatus.PENDING
        )) {

            throw new RuntimeException(
                    "Document already processed"
            );
        }

        doc.setStatus(DocumentStatus.APPROVED);

        documentRepository.save(doc);

        // SEND EMAIL
        emailService.sendDocumentApprovedEmail(
                doc.getUser().getEmail(),
                doc.getUser().getFullName(),
                doc.getDocumentType().name()
        );

        return "Document approved";
    }

    // ====================================
    // REJECT DOCUMENT
    // ====================================

    @Override
    @Transactional
    public String rejectDocument(Long id,
                                 String remark) {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() ->
                        new DocumentNotFoundException(
                                "Document not found"
                        ));

        if (!doc.getStatus().equals(
                DocumentStatus.PENDING
        )) {

            throw new RuntimeException(
                    "Document already processed"
            );
        }

        doc.setStatus(DocumentStatus.REJECTED);

        doc.setRemarks(remark);

        documentRepository.save(doc);

        // SEND EMAIL
        emailService.sendDocumentRejectedEmail(
                doc.getUser().getEmail(),
                doc.getUser().getFullName(),
                doc.getDocumentType().name(),
                remark
        );

        return "Document rejected";
    }
}