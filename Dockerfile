FROM maven:3.8.5-openjdk-18 AS build
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
