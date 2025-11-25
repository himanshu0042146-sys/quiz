# Quiz Management System — Project Plan

## 1. Overview

This document outlines the development plan for the **Quiz Management System**, based on the functional requirements provided. It covers scope, architecture, modules, timelines, and deliverables.

---

## 2. Objectives

* Provide a robust quiz creation and management platform for admins.
* Enable users to take quizzes with multiple question types.
* Ensure secure API access through authentication and role-based access control.
* Maintain clean, scalable backend architecture.

---

## 3. Functional Requirements Summary

### **Admin Features**

* Create, edit, delete quizzes.
* Add multiple questions per quiz.
* Support MCQ, True/False, and Text-based questions.
* Add and mark correct options for MCQs.
* Update/delete questions.
* Preview quiz before publishing.

### **User Features**

* View published quizzes.
* Attempt quizzes with different question types.
* Navigate through questions.
* Submit quiz.

### **API Endpoints**

* `POST api/admin/quiz/createQuiz`
* `GET api/quiz/getQuiz/{id}`
* `POST api/quiz/{id}/submit`
* `POST api/auth/login`

---

## 4. System Architecture

### **Tech Stack**

* **Backend:** Spring Boot, Spring Security, JWT
* **Database:** H2 (dev), PostgreSQL/MySQL (prod)
* **Build Tool:** Maven/Gradle
* **Auth:** JWT-based auth & role-based authorization

### **Layers**

* **Controller Layer:** API endpoints
* **Service Layer:** Business logic
* **Repository Layer:** Persistence operations
* **Entity Layer:** Quiz, Question, Option models

---

## 5. Database Schema

### **Entities**

#### **Quiz**

* id (Long)
* title (String)
* questions (List<Question>)

#### **Question**

* id (Long)
* questionText (String)
* questionType (Enum)
* options (List<Option>)

#### **Option**

* id (Long)
* optionText (String)
* correct (boolean)

---

## 6. Development Plan

### **Project Requirement**

* Initialize Spring Boot project
* Configure dependencies: Web, JPA, Security, JWT
* Set up folder structure
* Configure H2 database
* Implement login endpoint
* Implement JWT generation & validation
* Add role-based access control (Admin/User)
* Secure API paths using `SecurityFilterChain`
* Create Quiz, Question, Option entities
* Implement below API's
  * `createQuiz`,`getQuiz`,`submitQuiz`,`login` API
---
### **Scope of Improvement**

* Implementation of GlobalExceptionHandler using @ControllerAdvice for unified error handling.
* Add API Interceptors/Filters for logging, request validation, and monitoring.
* Apply Concurrency Control using synchronized blocks, optimistic/pessimistic locking (JPA), or Redis locks when required.
* Ensure clean architecture with reusable utility classes and proper design patterns.
---
