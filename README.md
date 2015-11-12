# tictactoe
Java Tic-Tac-Toe API!

To install and run, clone the repo and run the following commands:
```
mvn clean install
java -jar target/tictactoe-1.0-SNAPSHOT.jar server tictactoe.yml
```

This spins up a server at http://localhost:8080/

You can interact with the API using curl. To get a list of current game boards:
```
curl -X GET http://localhost:8080/boards/
```

To create a game board:
```
curl -X POST http://localhost:8080/boards/
```

To look at a game board without making a move (replace \<id\> with the board number):
```
curl -X GET http://localhost:8080/boards/<id>
```

To make a move, replace <id> with the board number, and \<row\> and \<col\> with the row and column you are picking:
```
curl -H "Content-Type: application/json" -X POST -d '{"row":"<row>","col":"<col>"}' http://localhost:8080/boards/<id>
```

Note: Row and column are zero-indexed, so the top left position is row 0, column 0 and the bottom right position
is row 2, column 2.
