# Use the official OpenJDK image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the target directory to the working directory in the container
COPY target/*.jar /app/ECommerceApp.jar

# Expose the port your application will run on
EXPOSE 8091

# Define the command to run your application
ENTRYPOINT ["java" ,"-jar", "/app/ECommerceApp.jar"]