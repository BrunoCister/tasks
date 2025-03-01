FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/tasks.jar tasks.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tasks.jar"]