SET ROOT_PATH=C:\Users\NS\IdeaProjects\task-management-api
psql -U postgres -h localhost -p 5432 < api_create_db_script.sql

psql -U task_owner -d task_dev_db -h localhost -p 5432 < api_data_script_local.sql

cd %ROOT_PATH%

start call .\src\main\resources\start-up\start-up-keycloak-local.bat

mvn clean install && START java -jar "-Dspring.profiles.active=local" target\task-management-api-0.0.1-SNAPSHOT.jar

exit