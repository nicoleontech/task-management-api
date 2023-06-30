# task-management-api

This is the backend rest api of a task management application.
It is built with spring-boot, uses Postgresql as a database and the
server stub was generated using OpenAPI Generator via its Maven plugin.

## How to set-up the needed dependencies

The application can run in a local environment as well as dockerized.
Depending on how you wish to start the application different setups are required.
For a local setup you need to install the following tools

Tech-stack local:
- Java >= 17 https://adoptium.net/temurin/releases/
- maven >= 3.9.0 https://maven.apache.org/download.cgi
- keycloak 21.1.1: https://www.keycloak.org/downloads
- postgresql 15: https://www.postgresql.org/download/

You need to make some adjustments in order for the start-up script to work.
For example, the installation root folder of the downloaded keycloak is required in one of the .bat files.
See `start-up-keycloak-local-guide.txt` for more help and make the required adjustments (wherever you see: *!!! change me* you need to
make these replacements providing your own local paths) in `start-up-keycloak-local.bat`.
You also need to change the first line of the `start-up-local.bat` and add the root folder of the cloned
task-management-api location.
It is also assumed that postgres runs in the default port "5432".
After having those tools installed and completing the changes in both `start-up-keycloak-local.bat`
and in `start-up-local.bat`, you can run the set-up script:
`start-up\start-up-local.bat`.

Tech-stack dockerized:
- Docker (Desktop): https://www.docker.com/products/docker-desktop/
- Java >= 17 https://adoptium.net/temurin/releases/
- maven >= 3.9.0 https://maven.apache.org/download.cgi

You need to change the first line of the `start-up-docker.bat` and add as root folder the cloned
task-management-api location. You also need to change in the `docker-compose.yaml` file the root path
of the cloned task-management-api location, which refers to the keycloak volume (line 21).
Then, you can run the set-up script:  `start-up\docker\start-up-docker.bat`.
