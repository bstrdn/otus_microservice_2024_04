FROM bellsoft/liberica-openjdk-alpine:21-x86_64
COPY /build/libs/*.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]