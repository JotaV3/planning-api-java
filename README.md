# Planning Dashboard

A financial planning and expense management application built with Spring Boot in the backend and Angular in the frontend.

This project is designed to help users organize their finances, track spending, and manage planning-related information in a clean and scalable way. The repository currently includes both the API and the frontend application, making it a full-stack project.

## Overview

Planning Dashboard is a monorepo that combines:

- `planning-api`: Java backend built with Spring Boot
- `planning-app`: Angular frontend application

The goal is to create a complete personal finance dashboard with a modern UI and a robust backend for managing financial data.

## Project Goals

- Track expenses and spending categories
- Manage financial planning data
- Provide a REST API for backend operations
- Deliver a user-friendly Angular frontend
- Keep the project modular and easy to extend

## Tech Stack

### Backend
- Java 27
- Spring Boot 4.1.2
- Maven
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 Database
- Validation
- Lombok
- MapStruct

### Frontend
- Angular 22
- TypeScript
- SCSS

## Repository Structure

```text
planning-api-java/
├── planning-api/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── .mvn/
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── planning-app/
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   ├── tsconfig.json
│   ├── tsconfig.app.json
│   ├── tsconfig.spec.json
│   ├── .editorconfig
│   ├── .prettierrc
│   └── README.md
│
├── .gitignore
├── .gitattributes
├── README.md
```

## Features

- REST API for financial data management
- CRUD operations for cards and related resources
- Validation and exception handling
- H2 database support for local development
- Angular frontend for UI interaction
- Easy setup for local development
- Structure ready for future expansion

## Current Status

This project is in active development. The backend is already in place with a Spring Boot API, and the frontend is included as an Angular application. The project is structured to evolve into a complete expense management dashboard.

## Prerequisites

Before running the application, make sure you have the following installed:

- Java 27 or newer
- Maven 3.9+
- Node.js 20+
- npm 10+

## Running the Backend

Navigate to the backend folder:

```bash
cd planning-api
./mvnw spring-boot:run
```

The API will start on:

```text
http://localhost:8080
```

The H2 database console is also available at:

```text
http://localhost:8080/h2-console
```

## Running the Frontend

Navigate to the frontend folder:

```bash
cd planning-app
npm install
npm start
```

The Angular app will run on:

```text
http://localhost:4200
```

## API Endpoints

The backend currently includes endpoints for card management, such as:

- `POST /cards`
- `GET /cards/{id}`
- `PUT /cards/{id}`

These endpoints are part of the core API and can be expanded as the project grows.

## Database Configuration

The project uses H2 as the local development database.

Configuration:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/planning-db
    driver-class-name: org.h2.Driver
    username: sa
    password: sa
```

This makes local setup simple and allows quick testing without needing an external database.

## Environment Setup

If you want to run the project locally:

1. Clone the repository
2. Start the backend with Maven
3. Install frontend dependencies with npm
4. Run the Angular application
5. Access the app in your browser

## Development Notes

The project follows a clean structure with separation between:

- API layer
- Service layer
- Repository layer
- DTO layer
- Validation utilities
- Exception handling

This approach keeps the application scalable and easier to maintain as new features are added.

## Roadmap

Planned improvements include:

- User authentication and authorization
- Expense categories and filters
- Dashboard analytics
- Charts and financial summaries
- Pagination and search
- Improved form validation
- Better UI/UX for the Angular app

## Author

JotaV3