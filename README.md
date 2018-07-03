# Readme

## Build and run

### Development mode
To develop the application follow steps

 * Run DB instance using docker-compose: `docker-compose -f docker-compose.yml up -d db`
 * Create DB connection configuretion in `.env` file:
```properties
DB_URL=jdbc:postgresql://hostname.of.remote.docker.host:5432/postgres
DB_USER=postgres
DB_PASSWORD=example
```
 * Execute migration scripts for DB: 
```bash
 cd db && ../gradlew flywayMigrate
```
 * Reimport Gradle project in IDE (DSL sources for present DB connection will be generated in module `dal`)


