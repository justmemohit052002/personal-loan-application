package com.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loanapp.dto.DocumentRequestDto;
import com.loanapp.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
	
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute DocumentRequestDto dto) {
    	
    	 System.out.println("Controller hit 🔥");
        return ResponseEntity.ok(documentService.uploadDocument(file, dto));
    }

    @GetMapping("/admin/pending")
    public ResponseEntity<?> getPending() {
        return ResponseEntity.ok(documentService.getPendingDocuments());
    }

    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.approveDocument(id));
    }

    @PostMapping("/admin/reject/{id}")
    public ResponseEntity<?> reject(
            @PathVariable Long id,
            @RequestParam String remark) {

        return ResponseEntity.ok(documentService.rejectDocument(id, remark));
    }
	

}
