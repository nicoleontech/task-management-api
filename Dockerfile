FROM maven:latest
VOLUME /tmp
COPY target/*.jar task-management-api.jar
COPY src/main/resources/* ./
ENTRYPOINT ["java","-jar","/task-management-api.jar"]