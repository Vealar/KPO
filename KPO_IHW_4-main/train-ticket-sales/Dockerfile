FROM openjdk:17-jdk-slim

WORKDIR /train-ticket-sales

COPY build/libs/train-ticket-sales-0.0.1-SNAPSHOT.jar app-sales.jar

ENTRYPOINT ["java", "-jar", "app-sales.jar"]

EXPOSE 8080