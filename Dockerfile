FROM openjdk:17-slim
WORKDIR /app
RUN apt-get update && apt-get install -y postgresql-client
COPY /target/flat_rents-1.0.0.jar .
CMD ["java", "-jar", "flat_rents-1.0.0.jar"]
