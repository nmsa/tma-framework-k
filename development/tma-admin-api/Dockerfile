FROM    maven:3.5.3-jdk-8-alpine

ENV     adminapi /atmosphere/tma/admin-api


#Adding TMA-Admin
WORKDIR ${adminapi}/tma-admin-api

# Prepare by downloading dependencies
ADD     pom.xml  ${adminapi}/tma-admin-api/pom.xml
RUN     ["mvn", "dependency:resolve"]
RUN     ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD     src      ${admin}/tma-admin-api/src
RUN     ["mvn", "clean", "install"]

RUN     ["cp", "-r", "bin", "/atmosphere/tma/admin-api/bin"]

CMD ["java", "-jar", "/atmosphere/tma/admin-api/bin/tma-admin-rest-web-0.0.1-SNAPSHOT.jar"]