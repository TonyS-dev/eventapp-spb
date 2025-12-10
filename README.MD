# EventApp-SPB 🚀
**Modern Event & Venue Management API**  
_A robust Spring Boot application for managing events and venues, featuring RESTful APIs, PostgreSQL integration, and hot-reload development workflow._

---

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptopenjdk.net/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)  
[![Build](https://img.shields.io/badge/build-Maven%203.9.0-yellow.svg)](https://maven.apache.org/)  
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

---

## 🌐 Live Demo

- **Local Development:** [http://localhost:8080](http://localhost:8080)

---

## 📖 Overview

EventApp-SPB is a backend service for managing events and venues. It provides RESTful APIs for CRUD operations, soft deletion, and restoration of both entities. Built with Spring Boot, it leverages PostgreSQL for persistence and supports hot-reload development via Docker and DevTools.

---

## 🛠️ Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.5.7
    - Spring Web, Spring Data JPA, Spring Validation, Spring DevTools
- Lombok
- SpringDoc OpenAPI (Swagger UI)

**Database & Infrastructure:**
- PostgreSQL 15 (Dockerized)
- Docker & Docker Compose

**Testing & Quality:**
- JUnit 5
- Spring Boot Test

**DevOps & Deployment:**
- Maven 3.9.0
- Dockerfile & Docker Compose
- Hot-reload with DevTools and LiveReload

---

## 📁 Project Structure

```plaintext
eventapp-spb/
├── src/
│   ├── main/
│   │   ├── java/com/codeup/eventapp/
│   │   │   ├── EventApp.java
│   │   │   ├── config/         # CORS, OpenAPI configs
│   │   │   ├── events/         # Event domain, usecases, controllers, DTOs
│   │   │   ├── venues/         # Venue domain, usecases, controllers, DTOs
│   │   │   ├── exception/      # Custom exceptions
│   │   │   ├── util/           # Utility classes
│   │   │   ├── web/            # Global advice, response wrappers
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/
│   │   │   ├── templates/
│   ├── test/
│   │   ├── java/com/codeup/eventapp/EventAppApplicationTests.java
│   │   ├── resources/application.properties
├── Dockerfile
├── Dockerfile.dev
├── docker-compose.yml
├── docker-compose.dev.yml
├── pom.xml
├── .env.example
├── start.sh / stop.sh / dev-watch.sh / logs.sh
└── HELP.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Java:** 17+
- **Maven:** 3.9.0+
- **Docker & Docker Compose:** Latest
- **inotify-tools:** For hot-reload (auto-installed in dev-watch.sh)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-org/eventapp-spb.git
   cd eventapp-spb
   ```

2. **Configure environment variables:**
    - Copy `.env.example` to `.env` and fill in your secrets:
      ```bash
      cp .env.example .env
      ```

3. **Start development environment:**
   ```bash
   ./start.sh
   ```

4. **Watch for code changes (hot-reload):**
   ```bash
   ./dev-watch.sh
   ```

5. **View logs:**
   ```bash
   ./logs.sh
   ```

6. **Stop environment:**
   ```bash
   ./stop.sh
   ```

---

### ⚙️ Environment Variables

See `.env.example` for all available variables:

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
- `APP_NAME`, `SERVER_PORT`, `SPRING_PROFILES_ACTIVE`
- `SPRING_DATABASE`, `SPRING_DATASOURCE_DRIVER`
- `SPRING_JPA_DATABASE_PLATFORM`, `SPRING_JPA_HIBERNATE_DDL_AUTO`, `SPRING_JPA_SHOW_SQL`
- `JWT_SECRET`, `SPRING_SECURITY_USER_NAME`, `SPRING_SECURITY_USER_PASSWORD`
- `JAVA_OPTS`

---

## ✅ Features

- Event CRUD: Create, Read, Update, Delete, Restore
- Venue CRUD: Create, Read, Update, Delete, Restore
- Soft deletion for events and venues
- Pagination and filtering
- Validation and error handling
- OpenAPI/Swagger documentation
- Hot-reload development workflow
- Dockerized database and app

---

## 📦 Usage

### Example: Create an Event

```http
POST /api/events
Content-Type: application/json

{
  "name": "Spring Boot Conference",
  "location": "Main Hall",
  "date": "2025-12-01T10:00:00",
  "description": "Annual developer conference",
  "venueId": 1
}
```

### Example: List Events

```http
GET /api/events?page=0&size=10
```

### Example: Create a Venue

```http
POST /api/venues
Content-Type: application/json

{
  "name": "Main Hall",
  "address": "123 Main St",
  "capacity": 500
}
```

---

## 📚 API Documentation

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec:** `/v3/api-docs`
- _See controllers for available endpoints: `/api/events`, `/api/venues`_

---

## 🧪 Testing

Run all tests with Maven:

```bash
mvn test
```

---

## 🚢 Deployment

### Docker Compose (Production)

```bash
docker compose up --build
```

### Docker Compose (Development, with hot-reload)

```bash
./start.sh
```

### Build Docker Image

```bash
docker build -t eventapp-spb .
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

---


