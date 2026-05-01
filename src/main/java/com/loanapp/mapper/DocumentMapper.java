package com.loanapp.mapper;

import org.springframework.stereotype.Component;

import com.loanapp.dto.DocumentResponseDto;
import com.loanapp.entity.Document;

@Component
public class DocumentMapper {
	  public DocumentResponseDto toDto(Document doc) {
	        DocumentResponseDto dto = new DocumentResponseDto();
	        dto.setId(doc.getId());
	        dto.setDocumentType(doc.getDocumentType());
	        dto.setFileUrl(doc.getFileUrl());
	        dto.setStatus(doc.getStatus());
	        dto.setRemarks(doc.getRemarks());
	        return dto;
	    }

}
