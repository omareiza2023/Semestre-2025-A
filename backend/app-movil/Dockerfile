# BUILD
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY . .
RUN mvn -B -DskipTests clean package

# RUNTIME
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
