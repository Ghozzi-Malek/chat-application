FROM eclipse-temurin:17-jdk AS build

WORKDIR /build

COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src

RUN chmod +x mvnw && ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /build/target/demo-0.0.1-SNAPSHOT.jar app.jar

USER 10001

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]