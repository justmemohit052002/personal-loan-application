package com.loanapp.controller;

import com.loanapp.dto.DocumentRequestDto;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.DocumentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ====================================
    // USER: UPLOAD DOCUMENT
    // ====================================

    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,

            @Valid
            @ModelAttribute DocumentRequestDto dto,

            @AuthenticationPrincipal
            CustomUserDetails userDetails) {

        // 🔐 SECURE AUTH USER
        Long userId =
                userDetails.getUser().getId();

        // 🔐 PREVENT USER ID TAMPERING
        dto.setUserId(userId);

        return ResponseEntity.ok(
                documentService.uploadDocument(
                        file,
                        dto
                )
        );
    }

    // ====================================
    // LOAN OFFICER APIs
    // ====================================

    // ====================================
    // GET PENDING DOCUMENTS
    // ====================================

    @GetMapping("/pending")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> getPending() {

        return ResponseEntity.ok(
                documentService.getPendingDocuments()
        );
    }

    // ====================================
    // APPROVE DOCUMENT
    // ====================================

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> approve(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                documentService.approveDocument(id)
        );
    }

    // ====================================
    // REJECT DOCUMENT
    // ====================================

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> reject(
            @PathVariable Long id,

            @RequestParam String remark) {

        return ResponseEntity.ok(
                documentService.rejectDocument(
                        id,
                        remark
                )
        );
    }
}