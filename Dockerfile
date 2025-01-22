# Step 1: Build the application using Maven
FROM maven:3.8.4-openjdk AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the project
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Create a smaller image for running the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/ChurchApplication-0.0.1-SNAPSHOT.jar /app/ChurchApplication-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/ChurchApplication-0.0.1-SNAPSHOT.jar"]
