FROM openjdk:8
COPY . /project
RUN cd /project
RUN cd /project ; ./gradlew clean build
RUN cp /project/build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
