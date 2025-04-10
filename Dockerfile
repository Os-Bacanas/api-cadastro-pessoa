FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine
COPY --from=build target/api-projeto.jar app.jar

EXPOSE 8080
CMD ["java","-jar","app.jar"]