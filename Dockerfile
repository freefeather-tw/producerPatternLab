FROM maven

EXPOSE 8080

RUN mkdir /opt/app
COPY . /opt/app

WORKDIR /opt/app
RUN mvn package
RUN cp target/app.jar /opt/app.jar
RUN rm -rf /opt/app
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "app.jar"]