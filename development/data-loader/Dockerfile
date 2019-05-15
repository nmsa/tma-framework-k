FROM    maven:3.5.3-jdk-8-alpine


ENV     data-loader     /atmosphere/tma/data-loader

#       Adding Planning Component
WORKDIR ${data-loader}/data-loader

#       Prepare by downloading dependencies
COPY    pom.xml         ${data-loader}/data-loader/pom.xml

#       Adding source, compile and package into a fat jar
COPY    src             ${data-loader}/data-loader/src
RUN     ["mvn", "install"]

RUN     ["cp", "-r", "bin", "/atmosphere/tma/data-loader/bin"]

CMD     ["java", "-jar", "/atmosphere/tma/data-loader/bin/data-loader-0.0.1-SNAPSHOT.jar"]
