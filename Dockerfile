FROM ubuntu:latest AS build

RUN apt-get update
RUN apt install openjdk-21-jdk -y
COPY . .

RUN apt install maven -y
RUN mvn clean install

FROM eclipse-temurin:21
EXPOSE 8080
COPY --from=build ./target/typetask-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
