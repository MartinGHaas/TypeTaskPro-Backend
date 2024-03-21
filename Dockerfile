FROM ubuntu:latest AS build

RUN sudo apt-get update
RUN sudo apt install openjdk-21-jre -y
COPY . .

RUN apt install maven -y
RUN mvn clean install

FROM opendk:21
EXPOSE 8080
COPY --from=build ./target/typetask-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]