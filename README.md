# 💰 Personal Loan Application System

> A secure, role-based Spring Boot backend for managing personal loan applications, document verification, credit score evaluation, and approval workflows using real-world banking logic.

---

# 🚀 Project Overview

The **Personal Loan Application System** is a backend application designed to simulate how modern banking and fintech loan systems operate.

The system supports:

- User registration & authentication
- Loan applications
- Document uploads & verification
- Credit score generation
- Loan approval/rejection workflow
- Role-based authorization
- Email notification system
- Secure REST APIs

This project follows a layered architecture using Spring Boot and demonstrates real-world backend engineering concepts used in banking platforms.

---

# 🏦 Business Workflow

```text
USER
 ↓
Register & Login
 ↓
Apply for Loan
 ↓
Upload Required Documents
 ↓
LOAN OFFICER Reviews Documents
 ↓
Credit Score Generated
 ↓
Loan Approved / Rejected
 ↓
Email Notifications Sent
```

---

# ✨ Features

## 👤 Authentication & User Management

- User Registration
- Secure Login
- JWT Authentication
- Password Encryption using BCrypt
- Role-Based Access Control (RBAC)

---

## 💰 Loan Management

- Apply for Personal Loan
- Track Loan Status
- View Loan History
- Loan Approval/Rejection Workflow
- Manual & Automated Decision Flow

---

## 📄 Document Verification

Users can upload:

- AADHAR
- PAN
- SALARY_SLIP
- BANK_STATEMENT

Loan officers can:

- Approve documents
- Reject documents with remarks
- Verify KYC workflow

---

## 🧠 Credit Score Engine

The system calculates a dynamic credit score based on:

- Previous loan history
- Approved/rejected loans
- Active loans
- Monthly income
- Loan affordability ratio
- Document verification status
- Loan amount risk

### Risk Levels

| Credit Score | Risk Level |
|---|---|
| 750+ | LOW |
| 650 - 749 | MEDIUM |
| Below 650 | HIGH |

---

## 📧 Email Notification System

Asynchronous email notifications are sent for:

- User Registration
- Loan Submission
- Loan Approval
- Loan Rejection
- Document Approval
- Document Rejection

Implemented using:

- Spring Mail
- Gmail SMTP
- Async Processing (`@Async`)
- Custom Thread Pool Executor

---

## 🔐 Security Features

- JWT Token Authentication
- Spring Security
- Role-Based Authorization
- Ownership Validation
- Protected APIs
- BCrypt Password Encoding

---

# 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java, Spring Boot |
| Database | MySQL |
| ORM | Hibernate / JPA |
| Security | Spring Security + JWT |
| Build Tool | Maven |
| API Testing | Postman |
| Email Service | Spring Mail SMTP |
| Authentication | JWT |
| Version Control | Git & GitHub |

---

# 📁 Project Structure

```bash
src/main/java/com/loanapp/
│
├── config/          # Security & Async Config
├── controller/      # REST Controllers
├── dto/             # Request & Response DTOs
├── entity/          # Database Entities
├── enums/           # Enum Classes
├── exception/       # Custom Exceptions
├── mapper/          # DTO Mappers
├── repository/      # JPA Repositories
├── security/        # JWT & Security Logic
├── service/         # Business Logic
└── util/            # Utility Classes
```

---

# ⚙️ Setup Instructions

## 🔽 Clone Repository

```bash
git clone https://github.com/your-username/personal-loan-application.git

cd personal-loan-application
```

---

# 🗄️ Configure MySQL Database

Create database:

```sql
CREATE DATABASE loan_db;
```

---

# ⚙️ Configure `application.properties`

```properties
server.port=8787

spring.datasource.url=jdbc:mysql://localhost:3306/loan_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
jwt.expiration=3600000
```

---

# 📧 Email Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587

spring.mail.username=yourgmail@gmail.com
spring.mail.password=your_app_password

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

# ▶️ Run Application

```bash
mvn clean install

mvn spring-boot:run
```

Application runs on:

```bash
http://localhost:8787
```

---

# 🔐 Roles in System

| Role | Responsibilities |
|---|---|
| USER | Apply loans, upload documents |
| LOAN_OFFICER | Verify docs, check credit score, approve/reject loans |
| ADMIN | View all loans, override statuses |

---

# 🧪 API Testing Flow

## ✅ USER FLOW

1. Register User
2. Login
3. Apply Loan
4. Upload Documents
5. View Loan Status

---

## ✅ LOAN OFFICER FLOW

1. Login as Officer
2. View Pending Loans
3. Verify Documents
4. Generate Credit Score
5. Approve / Reject Loan

---

## ✅ ADMIN FLOW

1. View All Loans
2. Override Loan Status
3. Monitor System Workflow

---

# 📄 Major APIs

## 🔐 Authentication APIs

| Method | Endpoint |
|---|---|
| POST | `/api/auth/register` |
| POST | `/api/auth/login` |

---

## 💰 Loan APIs

| Method | Endpoint |
|---|---|
| POST | `/api/loans/apply` |
| GET | `/api/loans/my` |
| GET | `/api/loans/{id}` |
| GET | `/api/loans/pending` |
| PUT | `/api/loans/{id}/approve` |
| PUT | `/api/loans/{id}/reject` |
| PUT | `/api/loans/{id}/final-decision` |

---

## 📄 Document APIs

| Method | Endpoint |
|---|---|
| POST | `/api/documents/upload` |
| GET | `/api/documents/pending` |
| PUT | `/api/documents/{id}/approve` |
| PUT | `/api/documents/{id}/reject` |

---

## 🧠 Credit Score APIs

| Method | Endpoint |
|---|---|
| GET | `/api/credit-score/{loanId}` |

---

# 🔥 Credit Score Logic Example

```text
Base Score
+ Income Analysis
+ Verified Documents
+ Good Loan History
- High Risk Loans
- Rejected Loan History
= Final Credit Score
```

---

# 🧩 Architecture Highlights

- Layered Architecture
- DTO Pattern
- Repository Pattern
- Async Email Processing
- Exception Handling
- Role-Based Workflow
- Secure API Design
- Banking-style Loan Lifecycle

---

# 📊 Current System Workflow

```text
REGISTER
   ↓
LOGIN
   ↓
APPLY LOAN
   ↓
UPLOAD DOCUMENTS
   ↓
VERIFY DOCUMENTS
   ↓
GENERATE CREDIT SCORE
   ↓
APPROVE / REJECT LOAN
   ↓
SEND EMAIL NOTIFICATIONS
```

---

 

# 🎯 Learning Outcomes

This project demonstrates:

- Spring Boot Backend Development
- JWT Authentication
- REST API Design
- Spring Security
- Async Programming
- Database Design
- Business Logic Engineering
- Fintech Workflow Modeling
- Credit Scoring Systems
- Enterprise Backend Architecture

 
