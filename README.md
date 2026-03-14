# Spring Boot Boilerplate Application

A minimal, production-friendly Spring Boot boilerplate that provides a common setup so you can start building business logic without worrying about authentication, database migrations, document management, or basic configuration.

## Description

This boilerplate provides a small but useful starting point for Spring Boot REST APIs with:

- JWT authentication (access + refresh tokens)
- User management endpoints (register, login, refresh, logout, get users)
- **Document upload and management with MinIO object storage**
- Database migrations via Liquibase
- **Global error handling with comprehensive exception management**
- **Utility classes for common operations (audit, date, enums, responses)**
- Common configuration for CORS, caching, and Jackson serialization
- OpenAPI / API documentation (SpringDoc) support

## Table of contents

- Features
- Tech stack
- Getting started
  - Prerequisites
  - Clone & run
- Configuration
  - Environment variables / application properties
  - Liquibase
  - MinIO configuration
- Security & authentication
- API endpoints (summary)
  - Authentication endpoints
  - Document management endpoints
- Global error handling
- Utility classes
- Developer notes
- API documentation
- Testing
- Contributing
- License
- Contact

## Features

- JWT-based auth with refresh tokens and token validation flows
- Public and private API separation (configurable)
- Password hashing for user registration
- **Document upload/download with MinIO object storage**
- **Presigned URL generation for secure document access**
- **Automatic directory organization by date and document type**
- **Global exception handling with structured error responses**
- **Security audit trail with automatic user tracking**
- **Utility classes for date operations, enum handling, and API responses**
- Liquibase for database migration management
- Basic HTTP CORS, cache and Jackson setup
- Gradle Kotlin DSL build (build.gradle.kts and Gradle wrapper included)

## Tech stack

- Language: Java
- Framework: Spring Boot
- Build: Gradle (Kotlin DSL)
- DB migrations: Liquibase
- Authentication: JWT (custom JwtUtil + refresh token flows)
- **Object Storage: MinIO**
- API documentation: SpringDoc / OpenAPI

## Getting started

### Prerequisites

- Java 17+ (or the JDK version the project targets)
- Gradle wrapper is included — no local Gradle install required
- A running relational database (MySQL, Postgres, etc.) for full functionality (Liquibase will apply migrations)
- **MinIO server for document storage functionality**

### Clone the repository

```bash
git clone https://github.com/jahid114/spring-boot-boilerplate-application.git
cd spring-boot-boilerplate-application
```

### Run the application (development)

```bash
./gradlew bootRun
```

Or build a jar and run:

```bash
./gradlew clean build
java -jar build/libs/spring-boot-boilerplate-application-<version>.jar
```

## Configuration

Typical environment variables / application.yml properties (examples)

```
# Datasource
spring.datasource.url=jdbc:mysql://localhost:3306/your_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=db_user
spring.datasource.password=db_pass

# JPA / Hibernate (if used)
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

# Liquibase
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

# JWT
jwt.secret=replace_with_secure_random_secret
jwt.accessTokenExpirationMs=900000        # 15 minutes
jwt.refreshTokenExpirationMs=1209600000   # 14 days

# MinIO Configuration
minio.url=http://localhost:9000
minio.access-key=your-minio-access-key
minio.secret-key=your-minio-secret-key
minio.bucket-name=your-bucket-name
minio.public-url-expiry=86400  # 24 hours in seconds

# Server
server.port=8080
```

Notes:
- Do not commit secrets into source control. Use environment variables or a secure secret manager for production.
- There may be an application-test.yml in the repo (ignored); use it for test-specific settings.
- **MinIO server must be running for document upload functionality**

## Database & Liquibase

- Liquibase is included to manage DB schema and data migrations. Place changelogs under `src/main/resources/db/changelog` and configure `spring.liquibase.change-log` accordingly.
- Typical workflow: ensure your DB is reachable, then start the application — Liquibase will apply pending changesets automatically.

## MinIO Configuration

The application includes comprehensive MinIO integration for document storage:

- **Automatic bucket connection testing on startup**
- **Document organization by type and date**
- **Presigned URL generation for secure access**
- **Support for various file types and content types**
- **Automatic cleanup of related entities on document deletion**

## Security & authentication

- JWT-based authentication is implemented.
- Token types include Access and Refresh tokens (TokenType).
- Public vs private endpoints:
  - Public endpoints (e.g., `/api/public/**` or configured values) are accessible without authentication.
  - Private endpoints require a valid access token.
- Provided auth flows (examples):
  - `POST /api/v1/auth/login` — authenticate and receive access + refresh tokens
  - `POST /api/v1/auth/refresh` — exchange refresh token for a new access token
  - `POST /api/v1/auth/logout` — invalidate the given refresh token (if token store or blacklist is used)
- Passwords are hashed on registration (bcrypt or equivalent).
- **Security audit trail automatically tracks which users perform actions**

## API Endpoints (summary)

The repository contains basic user, auth, and document endpoints. Example endpoints (actual paths may vary in code):

### Authentication Endpoints
- `POST /api/v1/users` — create/register user (stores hashed password)
- `GET /api/v1/users` — list users (protected)
- `POST /api/v1/auth/login` — login, returns tokens
- `POST /api/v1/auth/refresh` — refresh access token
- `POST /api/v1/auth/logout` — logout / invalidate token
- `GET /api/v1/public/...` — public endpoints

### Document Management Endpoints
- `DELETE /documents/{id}` — delete document by ID
- **Additional endpoints for upload/download can be added using DocumentService**

### Example: login curl

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user@example.com","password":"password"}'
```

## Global Error Handling

The application includes comprehensive global error handling via `@ControllerAdvice`:

- **Structured error responses with status codes and messages**
- **Validation error handling with detailed field-level feedback**
- **JWT and authentication-specific exception handling**
- **Data integrity violation handling**
- **Enum validation with helpful error messages**
- **Generic exception catching for unexpected errors**

All error responses follow a consistent format:
```json
{
  "status": 400,
  "message": "Detailed error message"
}
```

## Utility Classes

The boilerplate includes several utility classes for common operations:

### Response Utilities
- `ApiResponse` — Standard API response wrapper
- `ApiErrorResponse` — Structured error response with timestamp
- `EnumResponse` — Helper for enum-based responses

### Audit Utilities
- `SecurityAuditAware` — Automatic user tracking for audit fields
- Automatically captures current user ID for database audit columns

### Date Utilities
- `DateUtilityService` — Common date manipulation and formatting operations

### Enum Support
- Pre-defined enums for common application constants
- Enhanced enum validation and error handling

### Specification Utilities
- JPA Specification helpers for complex query building

## Developer notes

- Build system: Gradle (use included `./gradlew`)
- Main code under `src/main/java` — look for application class (e.g., `*Application`)
- Config classes: `CacheConfig`, `CorsConfig`, `JacksonConfig`, `MinioConfig`
- Security components: `JwtUtil`, `CustomUserDetailsService`, `TokenType`, `PublicApiEndpoints`
- Document management: `DocumentService`, `DocumentController`, `Document` entity
- Error handling: `GlobalExceptionHandler`, custom exception classes
- Utility packages: `utility.audit`, `utility.dateUtil`, `utility.response`, `utility.exceptions`
- Liquibase changelogs under `src/main/resources/db/changelog` (conventional)

## API documentation

- SpringDoc/OpenAPI is included in the commit history. When enabled, access:
  - Swagger UI: `/swagger-ui.html` or `/swagger-ui/index.html`
  - OpenAPI JSON: `/v3/api-docs`

## Testing

- Use the Gradle wrapper to run tests:

```bash
./gradlew test
```

## Contributing

- Feel free to open issues and PRs.
- Keep secrets and credentials out of commits.
- Add new Liquibase changesets for schema changes.
- Document API changes in the OpenAPI annotations or changelog docs.
- **For document features, ensure MinIO server is available for testing**

## License

- No license file is present in the repository. Add a `LICENSE` file (e.g., MIT) if you want one.

## Contact

- Repository owner: https://github.com/jahid114

---

Suggested improvements

- Add README badges: build, Maven/Gradle, coverage
- Add Dockerfile and docker-compose for local DB + MinIO + app
- Add CI (GitHub Actions) to run tests and checks
- Add an explicit LICENSE file
- Add instructions and sample `.env` or `application-local.yml` for quick setup
- **Add comprehensive API documentation for document endpoints**
