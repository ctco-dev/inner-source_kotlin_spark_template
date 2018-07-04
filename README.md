# Readme

## Build and run

### Development mode
To develop the application follow steps

 * Run DB instance using docker-compose: `docker-compose -f docker-compose.yml up -d db`
 or
 * Run DB instance using docker-compose with flyway migration scripts: 
 `docker-compose up`
 * Create DB connection configuretion in `.env` file:
```properties
DB_URL=jdbc:postgresql://hostname.of.remote.docker.host:5432/postgres
DB_USERNAME=postgres
DB_PASSWORD=example
```
* Reinitialize gradle project in your IDE to generate sources for Data Access Layer `dal` or build:
`./gradlew build`