# Build
# This is the latest gradle Docker image available on the client with JDK8 support
FROM gradle:4.7.0-jdk8
USER root

# Make client repos available inside this image if necessary
# And disable Gradle daemon
ENV GRADLE_USER_HOME /opt
COPY docker/gradle-config/ $GRADLE_USER_HOME/

# Copy project sources
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Copy Database migrations sources and scripts
COPY db ./db

# Copy Data Access Layer project sources
COPY dal ./dal

ENTRYPOINT ["gradle", "--no-daemon", "-s", "-i"]
CMD ["--help"]