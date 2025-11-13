# Hexagonal Architecture Transformation

## Overview
This document describes the transformation of the Event App from a traditional layered architecture to hexagonal architecture (also known as ports and adapters pattern).

## Architecture Layers

### 1. Domain Layer (`domain/model/`)
Contains pure business logic with no framework dependencies:
- **Event**: Event domain model with business properties
- **Venue**: Venue domain model with business properties

These models are framework-independent POJOs (Plain Old Java Objects).

### 2. Application Layer
#### Input Ports (`application/port/in/`)
Define use cases that can be executed:
- **EventUseCase**: Interface defining Event business operations
- **VenueUseCase**: Interface defining Venue business operations

#### Output Ports (`application/port/out/`)
Define contracts for external dependencies:
- **EventPersistencePort**: Interface for Event data persistence
- **VenuePersistencePort**: Interface for Venue data persistence

#### Services (`application/service/`)
Implement use cases and orchestrate domain logic:
- **EventService**: Implements EventUseCase
- **VenueService**: Implements VenueUseCase

### 3. Adapter Layer
#### Input Adapters (`adapter/in/web/`)
Handle external requests (REST API):
- **Controllers**: EventController, VenueController
- **DTOs**: Request/Response objects for API
- **Mappers**: Convert between DTOs and domain models

#### Output Adapters (`adapter/out/persistence/`)
Handle external persistence (Database):
- **Persistence Adapters**: EventPersistenceAdapter, VenuePersistenceAdapter
- **Mappers**: Convert between domain models and JPA entities

## Benefits

1. **Separation of Concerns**: Clear boundaries between business logic, application logic, and infrastructure
2. **Framework Independence**: Core business logic doesn't depend on Spring or JPA
3. **Testability**: Each layer can be tested independently with clear interfaces
4. **Flexibility**: Easy to swap adapters (e.g., REST → GraphQL, PostgreSQL → MongoDB)
5. **Maintainability**: Changes to one layer don't propagate to others

## Package Structure
```
src/main/java/com/codeup/eventapp/
├── application/
│   ├── port/
│   │   ├── in/           # Input ports (use cases)
│   │   └── out/          # Output ports (persistence)
│   └── service/          # Application services
├── domain/
│   └── model/            # Domain models (pure POJOs)
├── adapter/
│   ├── in/
│   │   └── web/          # REST controllers, DTOs, mappers
│   └── out/
│       └── persistence/  # JPA adapters and mappers
├── config/               # Spring configuration
├── entity/               # JPA entities
├── repository/           # JPA repository interfaces
├── exception/            # Custom exceptions
├── util/                 # Utilities
└── EventApp.java         # Main application
```

## Flow of a Request

1. **HTTP Request** → REST Controller (Input Adapter)
2. **Controller** → Converts DTO to Domain Model (using mapper)
3. **Controller** → Calls Use Case (Input Port)
4. **Service** → Implements Use Case, contains business logic
5. **Service** → Calls Persistence Port (Output Port)
6. **Persistence Adapter** → Implements Persistence Port
7. **Adapter** → Converts Domain Model to Entity, saves to database
8. **Response** flows back through the same layers

## Migration Summary

### Before (Layered Architecture)
- `web/controller/` - Controllers calling services directly
- `service/` - Service interfaces and implementations
- `repository/` - JPA repositories
- `domain/` - Loose domain models

### After (Hexagonal Architecture)
- `adapter/in/web/` - Input adapters (controllers, DTOs)
- `adapter/out/persistence/` - Output adapters (persistence)
- `application/port/` - Port interfaces (in/out)
- `application/service/` - Use case implementations
- `domain/model/` - Pure domain models

All existing functionality has been preserved, but the architecture is now more modular, testable, and maintainable.
