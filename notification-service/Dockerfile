# Stage #1: Build the JAR
FROM maven:3.10.0-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage #2: Run the JAR
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar notification-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
