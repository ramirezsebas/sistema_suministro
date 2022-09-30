mvn clean install -U
mvn clean package
mv target/sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar target/sistema-suministro-1.0-SNAPSHOT.jar
java -jar target/sistema-suministro-1.0-SNAPSHOT.jar