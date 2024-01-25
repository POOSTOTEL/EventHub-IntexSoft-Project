# Используйте официальный образ JDK 21
FROM openjdk:21

# Укажите рабочую директорию в контейнере
WORKDIR /app

# Копируйте файл .jar в рабочую директорию
COPY build/libs/EventHub-IntexSoft-0.0.1-SNAPSHOT.jar /app

# Запустите приложение
CMD ["java", "-jar", "EventHub-IntexSoft-0.0.1-SNAPSHOT.jar"]
