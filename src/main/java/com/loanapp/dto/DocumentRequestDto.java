package com.loanapp.dto;

import com.loanapp.enums.DocumentType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentRequestDto {
	@NotNull(message = "Document type is required")
	private DocumentType documentType;
	private Long userId;
	private Long loanId;
	
	public Long getUserId() { return userId; }
	public void setUserId(Long userId) { this.userId = userId; }
	
	public DocumentType getDocumentType() { return documentType; }
	public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

}
