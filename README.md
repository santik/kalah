# Kalah solution

### Chosen technologies
- Java 11
- SpringBoot
- JUnit

### Testing
`mvn clean test`  
98% of lines are covered.

### Running
`mvn clean package` - this will run tests and create jar file  
then `java -jar target/kalah-0.0.1-SNAPSHOT.jar`

### Calls to access the game 

Application will be running on port 7070. Port can be changed in application.properties

`curl --header "Content-Type: application/json" --request POST http://localhost:7070/games`
                
`curl --header "Content-Type: application/json" --request PUT http://localhost:7070/games/#GAME_ID#/pits/1`