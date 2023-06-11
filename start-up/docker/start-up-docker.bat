SET ROOT_PATH=C:\Users\NS\IdeaProjects\task-management-api

cd %ROOT_PATH%
mvn clean install && docker build . -t task-management:latest && docker-compose up -d

