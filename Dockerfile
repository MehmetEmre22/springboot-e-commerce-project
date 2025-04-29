# Use an OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy target jar (after build) into the container
COPY target/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8080

# Command to run your app
ENTRYPOINT ["java", "-jar", "app.jar"]