# **Makini University Payment Integration API**

A **robust REST API** for integrating **Cellulant Payment Gateway** with **Makini University's student payment system**.

---

## **📘 Table of Contents**
1. [Overview](#overview)  
2. [Features](#features)  
3. [Technology Stack](#technology-stack)  
4. [Project Structure](#project-structure)  
5. [Setup Instructions](#setup-instructions)  
6. [API Endpoints](#api-endpoints)  
7. [Database Schema](#database-schema)  
8. [Testing](#testing)  

---

## **🔍 Overview**

This API enables **Makini University** to:

- ✅ Validate students before payment processing  
- 💸 Receive instant payment notifications from **Cellulant**  
- 📊 Track payments by payment method (e.g., **M-PESA**, **Card**, **Bank Transfer**)  
- 🧾 Maintain comprehensive payment history  

---

## **⚙️ Features**

- **Student Validation** – Verify student eligibility before accepting payments  
- **Payment Notifications** – Real-time payment processing and notifications  
- **Payment Method Tracking** – Identify and categorize payments by method  
- **Duplicate Prevention** – Prevent duplicate transaction processing  
- **Comprehensive Logging** – Track all payment activities  
- **Error Handling** – Robust validation and error management  
- **RESTful Design** – Clean, industry-standard API design  

---

## **🧠 Technology Stack**

- **Java 21+** – Modern LTS version offering improved performance and language features  
- **Spring Boot 3.4.6** – Framework for building production-ready applications  
- **Spring Data JPA** – Simplifies database operations using repository abstraction and Hibernate ORM  
- **H2 Database** – Lightweight, in-memory database for development and testing  
- **PostgreSQL** – Production-ready relational database for persistent data storage  
- **Flyway** – Handles versioned database migrations for consistent schema evolution  
- **Maven** – Build automation and dependency management tool  
- **Lombok** – Reduces boilerplate code using annotations like `@Getter`, `@Setter`, and `@Builder`  
- **SLF4J (Logback)** – Unified logging API for structured and configurable logging  
- **Docker** – Containerization for consistent deployments across environments  

---

## **📂 Project Structure**


src/main/java/com/makini/university/
├── entity/
│ ├── Student.java # Student entity
│ └── Payment.java # Payment entity
├── repository/
│ ├── StudentRepository.java # Student data access
│ └── PaymentRepository.java # Payment data access
├── dto/
│ ├── StudentValidationRequest.java
│ ├── StudentValidationResponse.java
│ ├── PaymentNotificationRequest.java
│ ├── PaymentNotificationResponse.java
│ └── GenericResponse.java
├── service/
│ ├── StudentService.java # Student business logic
│ └── PaymentService.java # Payment processing logic
├── enums/
│ └── ResponseStatusEnum.java
├── controller/
│ └── PaymentIntegrationController.java # REST endpoints
├── config/
│ └── DataInitializer.java # Sample data loader
├── exception/
│ └── GlobalExceptionHandler.java
└── UniversityPaymentIntegrationApplication.java

---

## **🧰 Setup Instructions**

### **🔧 Prerequisites**
- Java **21** or higher  
- Maven **3.4+**  
- Git  
- *(Optional)* Docker Desktop  

---

### **⚙️ Installation Steps**

**1️⃣ Clone the repository**
```bash
git clone <your-repo-url>
cd makini-university-integration-api


2️⃣ Build the project
mvn clean install

3️⃣ Run the application locally

Ensure PostgreSQL is running locally, then update your application.yml:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/makini_university_db
    username: postgres
    password: cellulant123


Start the app:

mvn spring-boot:run


The API will be available at: http://localhost:8001

 Run via Docker
docker-compose up --build


This will start:

 PostgreSQL on port 5434

 Spring Boot App on port 8080

API Base URL (Docker): http://localhost:8080/api/v1/cellulant

 Access Database
docker exec -it makini_postgres psql -U postgres -d makini_university_db

Access Swagger UI
Environment	URL
Local	[http://localhost:8001/swagger-ui.html](http://localhost:8001/swagger-ui/index.html)

Docker	http://localhost:8080/swagger-ui.html
 Testing
To make testing easier, import the Postman collection included in this project:

**File:** `postman/Cellulant Intergration.postman_collection`


 Author

Carren Chepkorir
Software Engineer

🕓 Last Updated: October 27, 2025
