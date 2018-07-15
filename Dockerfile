FROM openjdk:8u171-alpine

WORKDIR /home/app

# Make client repos available inside this image if necessary
# And disable Gradle daemon
ENV GRADLE_USER_HOME /opt
COPY docker/gradle-config/ $GRADLE_USER_HOME/

COPY gradle/ ./gradle
COPY gradlew ./

# Copy project sources
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Copy Data Access Layer project sources
COPY dal ./dal

ENTRYPOINT ["./gradlew", "--no-daemon"]
CMD ["--help"]
