# EventApp - Spring Boot Event Management System

A comprehensive event and venue management REST API built with Spring Boot, following hexagonal architecture principles and modern development practices.

## 📋 Project Overview

EventApp is a microservices-ready application that manages events and venues with full CRUD operations, advanced JPA relationships, database migrations, and JWT-based security.

### Key Features

- ✅ **RESTful API** for Events and Venues management
- ✅ **Hexagonal Architecture** (Ports & Adapters pattern)
- ✅ **JPA/Hibernate** with PostgreSQL persistence
- ✅ **Flyway** database migrations
- ✅ **Advanced Relations** (OneToMany/ManyToOne between Venues and Events)
- ✅ **JWT Authentication** with Spring Security 6+
- ✅ **RFC 7807 Error Handling** (ProblemDetail)
- ✅ **Soft Delete** support for all entities
- ✅ **Bean Validation** for request DTOs
- ✅ **OpenAPI/Swagger** documentation
- ✅ **Docker** support with Docker Compose

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (Clean Architecture):

```
src/main/java/com/codeup/eventapp/
├── domain/                    # Business logic (core)
│   ├── model/                # Domain entities
│   ├── ports/in/             # Input ports (use cases)
│   └── ports/out/            # Output ports (repository interfaces)
├── application/              # Use case implementations
│   └── usecases/
├── infrastructure/           # External adapters
│   ├── adapters/            # Repository implementations
│   ├── controllers/         # REST controllers
│   ├── entities/            # JPA entities
│   ├── security/            # JWT & Security config
│   └── web/                 # DTOs, mappers, exception handlers
```

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 15+ (or use Docker)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/TonyS-dev/eventapp-spb.git
   cd eventapp-spb
   ```

2. **Start PostgreSQL with Docker**
   ```bash
   docker-compose up -d
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The API will be available at `http://localhost:8080`

### Environment Variables

Configure the following in `application.properties` or via environment:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/eventapp
spring.datasource.username=postgres
spring.datasource.password=postgres

# Flyway
spring.flyway.enabled=true

# Server
server.port=8080
```

## 📚 API Documentation

### Swagger UI
Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Authentication

**Login** (Mock credentials for demo):
```bash
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use the token in subsequent requests:
```bash
Authorization: Bearer <token>
```

### Venues API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/venues` | List all venues |
| GET | `/api/venues/{id}` | Get venue by ID |
| POST | `/api/venues` | Create new venue |
| PUT | `/api/venues/{id}` | Update venue |
| DELETE | `/api/venues/{id}` | Soft delete venue |
| GET | `/api/venues/deleted` | List deleted venues |
| PUT | `/api/venues/{id}/restore` | Restore deleted venue |

### Events API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/events` | List all events |
| GET | `/api/events/{id}` | Get event by ID |
| POST | `/api/events` | Create new event |
| PUT | `/api/events/{id}` | Update event |
| DELETE | `/api/events/{id}` | Soft delete event |
| GET | `/api/events/deleted` | List deleted events |
| PUT | `/api/events/{id}/restore` | Restore deleted event |

### Example: Create Venue

```bash
POST /api/venues
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Madison Square Garden",
  "address": "4 Pennsylvania Plaza, New York, NY 10001",
  "capacity": 20000
}
```

### Example: Create Event

```bash
POST /api/events
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Spring Boot Conference 2025",
  "location": "New York",
  "date": "2025-06-15T09:00:00",
  "description": "Annual Spring Boot developer conference",
  "venueId": 1
}
```

## 🗄️ Database Schema

### Venues Table
```sql
CREATE TABLE venues (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);
```

### Events Table
```sql
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255),
    date TIMESTAMP,
    description VARCHAR(1000),
    venue_id BIGINT REFERENCES venues(id),
    deleted BOOLEAN DEFAULT FALSE
);
```

## 🔧 Development

### Running Tests
```bash
./mvnw test
```

### Building for Production
```bash
./mvnw clean package
java -jar target/eventapp-0.0.1-SNAPSHOT.jar
```

### Docker Build
```bash
docker build -t eventapp:latest .
docker run -p 8080:8080 eventapp:latest
```

## 📦 Tech Stack

- **Framework**: Spring Boot 3.4.0
- **Language**: Java 17
- **Database**: PostgreSQL 15
- **ORM**: Hibernate/JPA
- **Migration**: Flyway
- **Security**: Spring Security 6 + JWT
- **Validation**: Jakarta Bean Validation
- **Documentation**: SpringDoc OpenAPI 3
- **Build Tool**: Maven
- **Containerization**: Docker

## 🌿 Git Workflow

This project follows **Gitflow** with 5 User Stories:

- `main` - Production-ready code
- `develop` - Integration branch
- `feat/User-Story-1` - In-Memory Catalog (Oct 30, 2025)
- `feat/User-Story-2` - JPA Persistence (Nov 7, 2025)
- `feat/User-Story-3` - Hexagonal Architecture (Nov 14, 2025)
- `feat/User-Story-4` - Relations & Flyway (Nov 21, 2025)
- `feat/User-Story-5` - Security & JWT (Dec 5, 2025)

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

**Tony S**
- GitHub: [@TonyS-dev](https://github.com/TonyS-dev)

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

---

**Built with ❤️ using Spring Boot**
