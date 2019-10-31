FROM    maven:3.5.3-jdk-8-alpine

ENV     adminapi /atmosphere/tma/admin-api


#Adding TMA-Admin
WORKDIR ${adminapi}

# Prepare by downloading dependencies
ADD     pom.xml  ${adminapi}/pom.xml
RUN     ["mvn", "dependency:resolve"]
RUN     ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD     src      ${adminapi}/src
RUN     ["mvn", "clean", "install"]

#RUN     ["cp", "-r", "bin", "/atmosphere/tma/admin-api/bin"]

CMD ["java", "-jar", "/atmosphere/tma/admin-api/bin/tma-admin-rest-0.0.1-SNAPSHOT.jar"]