FROM maven:4.0.0-openjdk-19 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml install -DskipTests

FROM openjdk:19
COPY --from=build /usr/src/app/target/student-service-main.jar /usr/app/student-service.jar
EXPOSE 1337
ENTRYPOINT ["java", "-jar", "/usr/app/student-service.jar"]
