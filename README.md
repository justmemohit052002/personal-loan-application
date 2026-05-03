# 💰 Personal Loan Application System

> A secure, role-based Spring Boot backend for managing personal loans, document verification, and approval workflows — designed with real-world banking logic.

---

## 🚀 Features

* 👤 User Management (Register, Login, Profile)
* 💰 Loan Application & Tracking
* 📄 Document Upload & Verification
* 🔐 JWT Authentication & Role-Based Authorization
* 🧑‍💼 Multi-role system (USER, LOAN_OFFICER, ADMIN)
* 🛡️ Secure APIs with access control
* 🔁 Loan Processing Workflow (PENDING → APPROVED/REJECTED)
* 🌐 RESTful APIs
* 🧩 Clean Layered Architecture

---

## 🛠️ Tech Stack

| Layer      | Technology            |
| ---------- | --------------------- |
| Backend    | Java, Spring Boot     |
| Database   | MySQL                 |
| ORM        | Hibernate / JPA       |
| Security   | Spring Security + JWT |
| Build Tool | Maven                 |
| Tools      | Postman, Git          |

---

## 📁 Project Structure

```bash
src/main/java/com/loanapp/
├── config/
├── controller/
├── dto/
├── entity/
├── enums/
├── repository/
├── security/
├── service/
└── util/
```

---

## ⚙️ Getting Started

### 🔽 Clone Repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

### 🗄️ Configure Database

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/loan_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### ▶️ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Server runs on:

```
http://localhost:8787
```

---

# 🧪 API TESTING GUIDE (POSTMAN)

---

## 🔐 1. AUTH APIs

### ✅ Register User

```http
POST /api/auth/register
```

```json
{
  "fullName": "Test User",
  "email": "user@test.com",
  "password": "123456",
  "mobileNumber": "9999999999",
  "city": "Pune",
  "state": "MH",
  "address": "India"
}
```

---

### ✅ Login

```http
POST /api/auth/login
```

```json
{
  "email": "user@test.com",
  "password": "123456"
}
```

👉 Save token:

```
Authorization: Bearer <TOKEN>
```

---

## 👤 2. USER FLOW

### 💰 Apply Loan

```http
POST /api/loans/apply
Authorization: Bearer USER_TOKEN
```

```json
{
  "amount": 50000,
  "tenure": 12,
  "monthlyIncome": 30000,
  "purpose": "Personal use"
}
```

---

### 📄 Get My Loans

```http
GET /api/loans/my
```

---

### 🔍 Get Loan by ID (OWN ONLY)

```http
GET /api/loans/{id}
```

---

## 📄 3. DOCUMENT FLOW

### 📤 Upload Document

```http
POST /api/documents/upload
Authorization: Bearer USER_TOKEN
```

**Body → form-data**

| Key          | Type | Value       |
| ------------ | ---- | ----------- |
| file         | File | Upload file |
| documentType | Text | AADHAR      |

---

### 🔎 Get Pending Documents

```http
GET /api/documents/pending
Authorization: Bearer LOAN_OFFICER_TOKEN
```

---

### ✅ Approve Document

```http
PUT /api/documents/{id}/approve
```

---

### ❌ Reject Document

```http
PUT /api/documents/{id}/reject?remark=Invalid
```

---

## 🧑‍💼 4. LOAN OFFICER FLOW

### 🔎 Get Pending Loans

```http
GET /api/loans/pending
```

---

### ✅ Approve Loan

```http
PUT /api/loans/{id}/approve
```

---

### ❌ Reject Loan

```http
PUT /api/loans/{id}/reject
```

---

## 👑 5. ADMIN FLOW

### 📊 Get All Loans

```http
GET /api/loans/all
```

---

### 🔄 Override Loan Status

```http
PUT /api/loans/{id}/status?status=APPROVED
```

Valid values:

```
PENDING, APPROVED, REJECTED
```

---

## 🔐 Authorization Header (IMPORTANT)

All protected APIs require:

```
Authorization: Bearer <TOKEN>
```

---

# 🧪 Recommended Testing Flow

1. Register USER
2. Login → get USER token
3. Apply loan
4. Upload document
5. Login as LOAN_OFFICER
6. Approve / Reject document
7. Approve / Reject loan
8. Login as ADMIN → override status

---

# ⚠️ Common Errors

| Error                          | Reason                        |
| ------------------------------ | ----------------------------- |
| 401 Unauthorized               | Missing/invalid token         |
| 403 Forbidden                  | Role not allowed              |
| Unauthorized access            | Accessing another user's data |
| Loan already processed         | Not in PENDING state          |
| Document already processed     | Already approved/rejected     |
| Missing request param (remark) | Reject without remark         |

---

# 🎯 Project Highlights

* 🔐 JWT-based secure authentication
* 🛡️ Role-based access control (USER, OFFICER, ADMIN)
* 📄 Document verification system
* 💰 Loan lifecycle management
* 🧠 Ownership-based data access
* 🧩 Clean architecture (Controller → Service → Repository)

---

# ⚠️ Current Limitation

> Loan approval is **not yet dependent on document verification**.

👉 Planned improvement:

```
Loan will be approved only if documents are VERIFIED
```

---

# 🚀 Future Enhancements

* 💸 EMI Calculation & Repayment System
* 📊 Dashboard (User / Officer / Admin)
* 📧 Notifications (Email/SMS)
* 📁 Cloud file storage (AWS S3)

---


