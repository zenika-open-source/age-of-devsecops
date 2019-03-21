# Backend

## Run a MySQL instance

```bash
sudo docker run \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_USER=game_scores \
  -e MYSQL_PASSWORD=password \
  -e MYSQL_DATABASE=labs_age_of_devsecops \
  -p 3306:3306 -d mysql
```

## Run the backend

*Game Engine* is a Spring Boot project written in Kotlin. You just have to run `main` in `AgeOfDevSecOpsApplication.kt`. The following environement
variables are required:

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/labs_age_of_devsecops
SPRING_DATASOURCE_USERNAME=game_scores
SPRING_DATASOURCE_PASSWORD=password
SPRING_SECURITY_USER_PASSWORD=game_engine_password
```

If you want to use a simulation of player actions, you need to run in the `dev` profile, i.e. add 
`-Dspring.profiles.active=dev` to the command line.

An IntelliJ run configuration is available in the project.

To pause the game engine:
```bash
curl -u "admin:game_engine_password" -X POST http://localhost:8080/api/admin/game/resume
```

To resume the game engine:
```bash
curl -u "admin:game_engine_password" -X POST http://localhost:8080/api/admin/game/pause
```

To enable player onboarding:
```bash
curl -u "admin:game_engine_password" -X POST http://localhost:8080/api/admin/onboarding/resume
```

To disable player onboarding:
```bash
curl -u "admin:game_engine_password" -X POST http://localhost:8080/api/admin/onboarding/pause
```
