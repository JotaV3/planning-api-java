# Planning API

A REST API for managing the cards in a personal finance dashboard. Planning API lets users create an account, authenticate securely, and manage their own payment-card records through protected endpoints.

> **What it does today:** provides user registration and login, issues JWT bearer tokens, and supports creating, retrieving, and updating cards associated with the authenticated user. It is a backend foundation for tracking financial information—not a transaction-processing or spending-analytics service.

## Features

- **Account registration and login** with BCrypt password hashing.
- **JWT authentication** using signed HS256 bearer tokens. Tokens are configured to expire after one hour.
- **User-scoped card management:** create a card, retrieve a card by ID, and update a card. Users can only access cards associated with their account.
- **Card data validation:** requires a card name and brand, and prevents duplicate card names for the same user.
- **Structured API errors** for unauthorized requests, missing resources, and invalid input.
- **H2 persistence** for local development, with a file-backed database by default and an in-memory database for tests.

## Tech stack

- Java 25
- Spring Boot 4.1.2, Spring Web MVC, and Spring Security
- Spring Data JPA and Hibernate
- H2 Database
- Jakarta Bean Validation
- Maven Wrapper
- MapStruct and Lombok

## Getting started

### Prerequisites

- JDK 25
- Git

### Configure the JWT secret

The application reads its signing key from the `JWT_SECRET` environment variable. Set it before starting the API, and use a strong, private value. Do not commit production secrets to the repository.

macOS/Linux:

```bash
export JWT_SECRET='replace-with-a-long-random-secret'
```

PowerShell:

```powershell
$env:JWT_SECRET = 'replace-with-a-long-random-secret'
```

### Run locally

```bash
git clone https://github.com/JotaV3/planning-api-java.git
cd planning-api-java
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080`. The local H2 console is available at `http://localhost:8080/h2-console`.

## API overview

Registration and login are public. All other API routes require a valid access token in the `Authorization` header:

```http
Authorization: Bearer <token>
```

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `POST` | `/auth/register` | Public | Create an account and receive a bearer token. |
| `POST` | `/auth/login` | Public | Authenticate and receive a bearer token. |
| `POST` | `/cards` | Authenticated | Create a card for the current user. |
| `GET` | `/cards/{id}` | Authenticated | Retrieve one of the current user's cards. |
| `PUT` | `/cards/{id}` | Authenticated | Update one of the current user's cards. |

Supported card brands: `VISA`, `MASTERCARD`, `AMERICAN_EXPRESS`, `DINERS`, and `ELO`.

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "login": "alex",
  "password": "strong-password",
  "name": "Alex"
}
```

A successful registration returns an authentication response with a token, its type (`Bearer`), its expiration duration in seconds, and the user's name. Passwords must contain at least 8 characters, and logins must be unique.

### Log in

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "login": "alex",
  "password": "strong-password"
}
```

Use the returned token to call protected endpoints.

### Create a card

```http
POST /cards
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "name": "Everyday card",
  "brand": "VISA"
}
```

A card's registration date is assigned by the API. Card names must be unique per user. The same name can be used by different users.

### Retrieve or update a card

Retrieve a card with `GET /cards/{id}`. To update it, send the card's new name and brand to `PUT /cards/{id}`:

```http
PUT /cards/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "name": "Travel card",
  "brand": "MASTERCARD"
}
```

The API returns `404 Not Found` when the card does not exist or is not owned by the authenticated user.

## Error responses

The API returns structured JSON errors. Validation failures use HTTP `422` and include invalid-field details; authentication failures use `401`, and missing resources use `404`.

## Run tests

```bash
./mvnw test
```

On Windows:

```bat
mvnw.cmd test
```

## Current scope

This project is under active development. The current card API supports create, get-by-ID, and update operations. Listing cards, deleting cards, transactions, budgets, and spending reports are not implemented yet.
