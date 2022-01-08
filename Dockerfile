FROM openjdk:17-alpine3.14
COPY ./target/app.jar /usr/src/plantlogic/
WORKDIR /usr/src/plantlogic
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]