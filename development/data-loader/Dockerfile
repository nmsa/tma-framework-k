FROM    tma-monitor/libs:0.1


ENV     dataloader     /atmosphere/tma/data-loader

#       Adding DataLoader Component
WORKDIR ${dataloader}/tma-data-loader

#       Prepare by downloading dependencies
COPY    pom.xml         ${dataloader}/tma-data-loader/pom.xml

#       Adding source, compile and package into a fat jar
COPY    src             ${dataloader}/tma-data-loader/src
RUN     ["mvn", "install"]

RUN     ["cp", "-r", "bin", "/atmosphere/tma/data-loader/bin"]

CMD     ["java", "-jar", "/atmosphere/tma/data-loader/bin/data-loader-0.0.1-SNAPSHOT.jar"]