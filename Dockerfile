FROM openjdk:17-slim
MAINTAINER ruslan
WORKDIR /app
COPY ./build/libs/*.jar app-keykloack-service.jar
ENTRYPOINT ["java", "-jar", "app-keykloack-service.jar"]