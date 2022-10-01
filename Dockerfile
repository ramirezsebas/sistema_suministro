FROM maven:3.8.6-eclipse-temurin-11-alpine
WORKDIR /usr/src/sistema_suministro
COPY . .
RUN mvn clean install -U -f pom-docker.xml
RUN cat mainClassList | xargs -i sh -c "sed -i 's/server.*</{}/' pom-docker.xml && mvn clean package -f pom-docker.xml && mv target/sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar target/sistema-suministro-1.0-SNAPSHOT.jar '{}'.jar"
RUN apk add iproute2