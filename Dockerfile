# Используем базовый образ с OpenJDK 17
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Обновляем и устанавливаем необходимые пакеты
RUN apt-get update && apt-get install -y postgresql-client

# Копируем JAR-файл в контейнер
COPY target/flat_rents-1.0.0.jar /app/flat_rents-1.0.0.jar

# Запускаем приложение
CMD ["java", "-jar", "flat_rents-1.0.0.jar"]
