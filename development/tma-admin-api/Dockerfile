FROM    maven:3.5.3-jdk-8-alpine

ENV     admin /atmosphere/tma/admin


#Adding TMA-Admin
WORKDIR ${admin}/tma-admin

# Prepare by downloading dependencies
ADD     pom.xml  ${admin}/tma-admin/pom.xml
RUN     ["mvn", "dependency:resolve"]
RUN     ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD     src      ${admin}/tma-admin/src
RUN     ["mvn", "clean", "install"]

RUN     ["cp", "-r", "bin", "/atmosphere/tma/admin/bin"]

CMD ["java", "-jar", "/atmosphere/tma/admin/tma-admin/bin/tma-admin-rest-0.0.1-SNAPSHOT.jar"]