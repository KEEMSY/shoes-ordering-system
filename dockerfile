FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-16-jdk
ARG JAR_FILE=*.jar
COPY build/libs/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
