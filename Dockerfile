FROM openjdk:8u171-alpine

WORKDIR /usr/src/app

# Make client repos available inside this image if necessary
# And disable Gradle daemon
ENV GRADLE_USER_HOME /opt
COPY docker/gradle-config/ $GRADLE_USER_HOME/

COPY gradle/ ./gradle
COPY gradlew ./

# Copy project sources
COPY build.gradle.kts settings.gradle.kts ./

# Copy Data Access Layer project sources
COPY dal ./dal

# Convert gradlew file to unix format
RUN ["dos2unix", "./gradlew"]

# Download all dependencies
RUN ["./gradlew", "install"]

COPY src ./src

ARG APP_VERSION_ARG
ENV APP_VERSION=$APP_VERSION_ARG

RUN ["./gradlew", "shadowJar", "--no-daemon", "--console=plain"]

ENTRYPOINT ["./gradlew", "--no-daemon", "-s", "-i"]
CMD ["--help"]
