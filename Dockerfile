# Build Application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

# Run Application
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/spaceship.jar spaceship.jar

EXPOSE 8080

#Run commando for .jar
ENTRYPOINT ["java", "-jar", "spaceship.jar"]
