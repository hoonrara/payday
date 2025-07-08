# Java 21로 내 앱 실행하는 컨테이너를 만든다
FROM openjdk:21-jdk-slim

WORKDIR /app

# 코드 복사하고 빌드
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src src/

RUN ./gradlew bootJar --no-daemon

# 포트 열고 앱 실행
EXPOSE 8080
CMD ["java", "-jar", "build/libs/payday-0.0.1-SNAPSHOT.jar"]
