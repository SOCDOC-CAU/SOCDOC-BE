FROM openjdk:11-slim as builder

WORKDIR /build

COPY . ./
COPY config/application.properties ./src/main/resources/application.properties

RUN ./gradlew build

FROM openjdk:11-slim

WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./socdoc.jar
COPY --from=builder /build/config/serviceAccountKey.json ./serviceAccountKey.json

EXPOSE 8080
CMD ["java", "-jar", "socdoc.jar"]
