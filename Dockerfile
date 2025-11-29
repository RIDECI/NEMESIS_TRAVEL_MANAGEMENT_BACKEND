FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=builder /app/${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "echo '>>> RAILWAY PORT IS: '$PORT; java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]