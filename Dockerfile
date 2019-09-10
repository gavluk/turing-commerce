FROM openjdk:8
COPY ./build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
