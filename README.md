# Snacpack - Portfolio Generator using GitHub

Snacpack is a Spring Boot application that generates a personal portfolio webpage using a GitHub username. It fetches public user data and repositories from the GitHub REST API and dynamically displays them using a clean, responsive UI built with Thymeleaf.

## ✨ Features

- 🔍 Fetch GitHub user data and repositories via REST API
- 🎨 Display profile and projects in a portfolio format
- 📄 MVC architecture (Controller - Service - Repository - Model)
- 📦 Integrated with PostgreSQL for storing custom data
- ✅ Input validation using Hibernate Validator

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Web / WebFlux
- Spring Data JPA
- Spring Validation
- Thymeleaf
- PostgreSQL
- Lombok
- Maven

## 🧩 Dependencies

- **REST API** (`spring-boot-starter-web`)  
- **UI** (`spring-boot-starter-thymeleaf`)  
- **Database** (`spring-boot-starter-data-jpa` + `postgresql`)  
- **Validation** (`spring-boot-starter-validation`)  
- **GitHub API calls** (`spring-boot-starter-webflux`)  
- **Boilerplate reduction** (`lombok`)  

## 📌 Future Enhancements

- OAuth login via GitHub
- Portfolio PDF generation
- Custom themes and color options

