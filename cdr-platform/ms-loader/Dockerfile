# Base image with Java 21
FROM openjdk:21-slim

# working directory in container
WORKDIR /app

# the JAR file
COPY target/ms-loader-0.0.1-SNAPSHOT.jar app.jar

# directories for CDR files
RUN mkdir -p /app/data/incoming /app/data/processed /app/data/failed

# port 8082 (from properties file)
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]