FROM openjdk:17-jdk-slim

WORKDIR /train-ticket-authorization

COPY build/libs/train-ticket-authorization-0.0.1-SNAPSHOT.jar app-authorization.jar

ENTRYPOINT ["java", "-jar", "app-authorization.jar"]

EXPOSE 8081