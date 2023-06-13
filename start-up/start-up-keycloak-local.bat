SET KEYCLOAK_ADMIN=admin
SET KEYCLOAK_ADMIN_PASSWORD=admin
SET KC_HOSTNAME_STRICT_HTTPS=false
SET KC_HTTP_PORT=8088
SET KC_HTTP_RELATIVE_PATH=/auth
SET KC_HOSTNAME_ADMIN=localhost
SET KC_HOSTNAME=localhost
SET KC_DB_URL=jdbc:postgresql://localhost:5432/task_dev_db?user=task_owner

CD C:\Users\NS\Downloads\keycloak-21.1.1\keycloak-21.1.1
MKDIR data\import

XCOPY C:\Users\NS\IdeaProjects\task-management-api\src\main\resources\start-up\realm-export.json C:\Users\NS\Downloads\keycloak-21.1.1\keycloak-21.1.1\data\import\realm-export.json*

C:\Users\NS\Downloads\keycloak-21.1.1\keycloak-21.1.1\bin\kc.bat --verbose start-dev --db postgres --import-realm