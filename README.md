## Workspace preparation

### All
You need to have a file named `.env` in the root directory, containing the following properties: 
```properties
DB_URL=jdbc:postgresql://hostname.of.remote.docker.host:5432/postgres
DB_USERNAME=postgres
DB_PASSWORD=example
```

There's a `sample.env` file in the root directory to serve as inspiration source.

### If using a remote shared `DOCKER_HOST`
In such case you should create a `docker-compose.override.yml` file in the root directory. Consult you environment's Wiki on how to do it.

#### Building in Docker
If your environment mandates using internal repositories, you should create a `./docker/gradle-config/init.gradle` file with URLs to internal dependency and Gradle plugin repositories.

If it's necessary to use a proxy to access external resources, you should create a `./docker/gradle-config/gradle.properties` file, specifying the proxy host and port. 

There're `init.gradle.stub` and `gradle.properties.stub` files in `./docker/gradle-config/` to serve as inspiration sources.

## Dockerized End-To-End build and run

An End-To-End run of the application consists of:  

1. init and run a Postgresql Docker container
2. apply all existing DB migrations using Flyway
3. build and run the app using this database and exposing a default port `4567` 

To perform it just do `docker-compose up -d` in your console

## Development mode

* Reinitialize gradle project in your IDE to generate sources for Data Access Layer `dal` or build:
`./gradlew build`
