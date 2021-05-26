FROM openjdk:11-ea-9-jdk-slim
EXPOSE 8091
RUN addgroup spring
RUN adduser --ingroup spring  spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
