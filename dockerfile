FROM  eclipse-temurin:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV BOT_NAME = "заменяемое значение при запуске контейнера"
ENV BOT_TOKEN = "заменяемое значение при запуске контейнера"
ENV BOT_DB_USERNAME=jrtb_db_user
ENV BOT_DB_PASSWORD=jrtb_db_password
#ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]
ENTRYPOINT ["java", "-jar", "/app.jar"]