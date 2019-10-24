FROM    maven:3.5.3-jdk-8-alpine

ENV     adminweb /atmosphere/tma/admin-web


#Adding TMA-Admin
WORKDIR ${adminweb}/tma-admin-web

# Prepare by downloading dependencies
ADD     pom.xml  ${adminweb}/tma-admin-web/pom.xml
RUN     ["mvn", "dependency:resolve"]
RUN     ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD     src      ${adminweb}/tma-admin-web/src
RUN     ["mvn", "clean", "install"]

RUN     ["cp", "-r", "bin", "/atmosphere/tma/admin-web/bin"]

CMD ["java", "-jar", "/atmosphere/tma/admin-web/bin/tma-admin-rest-0.0.1-SNAPSHOT.jar"]