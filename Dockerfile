FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src /build/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /build/target/delivery-service*.jar /app/service.jar
ENTRYPOINT exec java -jar service.jar