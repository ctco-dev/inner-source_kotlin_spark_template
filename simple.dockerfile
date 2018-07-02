FROM openjdk:10-jre-slim
VOLUME /tmp
COPY build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]