// EmailServiceImpl.java

package com.loanapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    // ====================================
    // COMMON EMAIL METHOD
    // ====================================

    private void sendEmail(String to,
                           String subject,
                           String body) {

        try {

            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(senderEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            logger.info(
                    "Email sent successfully to: {}",
                    to
            );

        } catch (MailException e) {

            logger.error(
                    "Mail sending failed for: {}",
                    to,
                    e
            );

        } catch (Exception e) {

            logger.error(
                    "Unexpected email error",
                    e
            );
        }
    }

    // ====================================
    // REGISTRATION EMAIL
    // ====================================

    @Override
    @Async
    public void sendRegistrationEmail(String to,
                                      String name) {

        String subject = "Welcome to LoanApp";

        String body =
                "Hello " + name + ",\n\n" +
                "Your account has been created successfully.\n\n" +
                "Thank you for choosing LoanApp.";

        sendEmail(to, subject, body);
    }

    // ====================================
    // LOAN SUBMITTED EMAIL
    // ====================================

    @Override
    @Async
    public void sendLoanSubmittedEmail(String to,
                                       String name,
                                       Long loanId) {

        String subject = "Loan Application Submitted";

        String body =
                "Hello " + name + ",\n\n" +
                "Your loan application has been submitted.\n\n" +
                "Loan ID: " + loanId;

        sendEmail(to, subject, body);
    }

    // ====================================
    // LOAN APPROVED EMAIL
    // ====================================

    @Override
    @Async
    public void sendLoanApprovedEmail(String to,
                                      String name,
                                      Long loanId) {

        String subject = "Loan Approved";

        String body =
                "Congratulations " + name + ",\n\n" +
                "Your loan has been approved.\n\n" +
                "Loan ID: " + loanId;

        sendEmail(to, subject, body);
    }

    // ====================================
    // LOAN REJECTED EMAIL
    // ====================================

    @Override
    @Async
    public void sendLoanRejectedEmail(String to,
                                      String name,
                                      Long loanId) {

        String subject = "Loan Rejected";

        String body =
                "Hello " + name + ",\n\n" +
                "Your loan application has been rejected.\n\n" +
                "Loan ID: " + loanId;

        sendEmail(to, subject, body);
    }

    // ====================================
    // DOCUMENT APPROVED EMAIL
    // ====================================

    @Override
    @Async
    public void sendDocumentApprovedEmail(String to,
                                          String name,
                                          String documentType) {

        String subject = "Document Approved";

        String body =
                "Hello " + name + ",\n\n" +
                "Your document has been approved.\n\n" +
                "Document Type: " + documentType;

        sendEmail(to, subject, body);
    }

    // ====================================
    // DOCUMENT REJECTED EMAIL
    // ====================================

    @Override
    @Async
    public void sendDocumentRejectedEmail(String to,
                                          String name,
                                          String documentType,
                                          String remark) {

        String subject = "Document Rejected";

        String body =
                "Hello " + name + ",\n\n" +
                "Your document has been rejected.\n\n" +
                "Document Type: " + documentType + "\n" +
                "Reason: " + remark;

        sendEmail(to, subject, body);
    }
}