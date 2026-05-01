# 💰 Personal Loan Application System

> A Spring Boot backend for managing personal loans, users, and application workflows — structured, scalable, and API-driven.

---

## 🚀 Features

* 👤 User Management (Register, View)
* 📄 Loan Application Processing
* 🔁 CRUD Operations
* 🔐 JWT Authentication & Role-Based Authorization
* 🧑‍💼 Multi-role system (USER, LOAN_OFFICER, ADMIN)
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
```

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

## 🧑‍💼 3. LOAN OFFICER FLOW

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

## 👑 4. ADMIN FLOW

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

* PENDING
* APPROVED
* REJECTED

---

## 🔐 Authorization Header (Important)

All protected APIs require:

```
Authorization: Bearer <TOKEN>
```

---

## 🧪 Recommended Testing Flow

1. Register user
2. Login → get token
3. Apply loan
4. Login as Loan Officer → approve/reject
5. Login as Admin → override

---

## ⚠️ Common Errors

| Error                  | Reason                        |
| ---------------------- | ----------------------------- |
| 401 Unauthorized       | Missing/invalid token         |
| 403 Forbidden          | Role not allowed              |
| Unauthorized access    | Accessing another user's loan |
| Loan already processed | Not in PENDING state          |
| Invalid status         | Wrong enum value              |

---

## 🎯 Project Highlights

* Secure JWT-based authentication
* Role-based + ownership-based access control
* Real-world loan workflow (User → Officer → Admin)
* Clean architecture (Controller → Service → Repository)

---

## 📄 License

MIT License © 2026
