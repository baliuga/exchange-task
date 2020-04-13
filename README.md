# exchange-task

mvn clean install -DskipTests

java -jar exchange-0.0.1-SNAPSHOT.jar

*To run PostgreSQL in Docker:*

docker run --name psql-docker-container -p 5432:5432 -e POSTGRES_DB=testdb -e POSTGRES_PASSWORD=mysecretpassword -d postgres

*And change app.properties and pom.xml accordingly*

