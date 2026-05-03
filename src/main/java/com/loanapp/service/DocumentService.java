package com.loanapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.loanapp.dto.DocumentRequestDto;
import com.loanapp.dto.DocumentResponseDto;

public interface DocumentService {
	String uploadDocument(MultipartFile file, DocumentRequestDto dto);

	List<DocumentResponseDto> getPendingDocuments();

	String approveDocument(Long id);

	String rejectDocument(Long id, String remark);

}
