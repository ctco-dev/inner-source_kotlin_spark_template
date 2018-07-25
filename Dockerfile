FROM openjdk:8u171-alpine

WORKDIR /usr/src/app

# Make client repos available inside this image if necessary
# And disable Gradle daemon
ENV GRADLE_USER_HOME /opt
COPY docker/gradle-config/ $GRADLE_USER_HOME/

#
#RUN mkdir /opt/wrapper
#VOLUME /opt/wrapper
#
#RUN mkdir /opt/caches
#VOLUME /opt/caches

COPY gradle/ ./gradle
COPY gradlew ./

# Copy project sources
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Copy Database migrations sources and scripts
COPY db ./db

# Copy Data Access Layer project sources
COPY dal ./dal

ARG APP_VERSION_ARG
ENV APP_VERSION=$APP_VERSION_ARG

# Convert gradlew file to unix format
RUN ["dos2unix", "./gradlew"]

ENTRYPOINT ["./gradlew", "--no-daemon", "-s", "-i"]
CMD ["--help"]
