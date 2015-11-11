package net.joshstevens.tictactoe.resources;

import net.joshstevens.tictactoe.models.Game;
import net.joshstevens.tictactoe.models.Move;
import net.joshstevens.tictactoe.models.Square;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by joshstevens on 11/8/15.
 *
 * This file describes the "/boards" endpoint.
 *
 * A user can POST to "/boards" which will create a new board.
 * A user can GET "/boards/{gameID}" which will show them that board and tell them whose turn it is.
 * A user can GET "/boards" which will show a list of boards.
 */

@Path("/boards")
public class BoardsResource {

    @Context
    private UriInfo uriInfo;
    private List<Game> games = new ArrayList<Game>();
    private AtomicInteger newId = new AtomicInteger();

    @POST
    public Response createBoard() {
        // Create new game board
        int id = newId.incrementAndGet();
        Game game = new Game(id);
        games.add(game);
        return Response.created(new GameResource(game, uriInfo).getUri()).build();
    }

    @GET
    @Produces("application/json")
    public List<String> getBoards() {
        return games.stream()
                .map(game -> new GameResource(game, uriInfo))
                .map(GameResource::getUri)
                .map(URI::toString)
                .collect(Collectors.toList());
    }

    @Path("{id}")
    @GET
    @Produces("text/plain")
    public String getGame(@PathParam("id") int id) {
        StringBuilder board = new StringBuilder("Game ID does not exist.\n");
        Square[][] gameBoard = games.get(id-1).getBoard();

        // Check for non existent game.
        GameResource reqGame = games.stream()
                                .filter(game -> id == game.getId())
                                .findFirst()
                                .map(game -> new GameResource(game, uriInfo))
                                .orElse(null);
        if (reqGame == null) return board.toString();

        // Game exists, construct response string.
        board.delete(0,24);
        for (Square[] row : gameBoard) {
            if (row[0] == Square.EMPTY) board.append(" |");
            else if (row[0] == Square.X) board.append("X|");
            else board.append("O|");

            if (row[1] == Square.EMPTY) board.append(" |");
            else if (row[1] == Square.X) board.append("X|");
            else board.append("O|");

            if (row[2] == Square.EMPTY) board.append(" \n");
            else if (row[2] == Square.X) board.append("X\n");
            else board.append("O\n");

            board.append("-----\n");
        }
        board.delete(30,37);
        board.append("Next player: " + reqGame.currentPlayer + "\n");


        return board.toString();
    }

    @Path("{id}")
    @POST
    @Consumes("application/json")
    public Response attemptMove(Move moveRequest, @PathParam("id") int id) {
        int row = moveRequest.row;
        int col = moveRequest.col;
        Response r;
        Game reqGame = games.get(id-1);
        if (reqGame.checkSquare(row, col)) {
            reqGame.makeMove(row, col);
            r = Response.status(201).entity("Move completed.").build();
        } else {
            r = Response.status(409).entity("That square is occupied.").build();
        }

        return r;
    }
}
