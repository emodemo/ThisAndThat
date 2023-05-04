#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM selenium/standalone-firefox-debug:3.4.0-einsteinium
RUN apt-get update && apt-get install -y iputils-ping

