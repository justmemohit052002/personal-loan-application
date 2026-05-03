package com.loanapp.dto;

import com.loanapp.enums.DocumentStatus;
import com.loanapp.enums.DocumentType;

import lombok.Data;

@Data
public class DocumentResponseDto {
	private Long id;
    private DocumentType documentType;
    private String fileUrl;
    private DocumentStatus status;
    private String remarks;


}
