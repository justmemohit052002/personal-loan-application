package com.loanapp.service;

public interface EmailService {

    void sendRegistrationEmail(String to,
                               String name);

    void sendLoanSubmittedEmail(String to,
                                String name,
                                Long loanId);

    void sendLoanApprovedEmail(String to,
                               String name,
                               Long loanId);

    void sendLoanRejectedEmail(String to,
                               String name,
                               Long loanId);

    void sendDocumentApprovedEmail(String to,
                                   String name,
                                   String documentType);

    void sendDocumentRejectedEmail(String to,
                                   String name,
                                   String documentType,
                                   String remark);
}