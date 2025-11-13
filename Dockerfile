# Stage 1: Build with Maven and JDK 17
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml to leverage dependency cache
COPY pom.xml .

# Download dependencies to cache them
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the package (skip tests to speed up)
RUN mvn clean package -DskipTests

# Stage 2: Final image with Temurin 17 JRE Alpine (lighter)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
