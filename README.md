# task-management-api 
This is the backend rest api of a task management application. It is built with spring-boot and uses Postgresql and the
server stub was generated using OpenAPI Generator via its Maven plugin.
## How to start-up dependencies
keycloak-start up:
`docker run -p 8088:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e KEYCLOAK_IMPORT=\home\keycloak\realm-export.json -v C:\Users\NS\Downloads\realm-export.json:\home\keycloak\realm-export.json jboss/keycloak`
