#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Information about the maintainer of the image
MAINTAINER hieuptit.com

# Add the application's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar app/accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "app/accounts-0.0.1-SNAPSHOT.jar"]