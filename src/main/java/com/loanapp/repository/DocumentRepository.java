package com.loanapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loanapp.entity.Document;
import com.loanapp.enums.DocumentStatus;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByStatus(DocumentStatus status);

    List<Document> findByUserId(Long userId);

    // 🔥 NEW
    List<Document> findByLoanId(Long loanId);
}