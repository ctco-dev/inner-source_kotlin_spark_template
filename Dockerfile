# Build
FROM gradle:4.8.0-jdk10 as build
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle shadowJar --no-daemon

# Run
FROM openjdk:10-jre-slim as runtime
WORKDIR root
COPY --from=build /home/gradle/build/libs/app.jar .
VOLUME /tmp
CMD ["java", "-jar", "app.jar"]
