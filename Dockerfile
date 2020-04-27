FROM openjdk:8
ADD target/projectscheduler.jar projectscheduler.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "projectscheduler.jar"]