FROM openjdk:21-jdk-slim
WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src src/
COPY src/main/resources/application.yml ./config/

RUN ./gradlew bootJar --no-daemon

EXPOSE 8080

CMD ["java", "-Dspring.config.location=classpath:/application.yml, file:./config/application.yml", "-jar", "build/libs/payday-0.0.1-SNAPSHOT.jar"]
