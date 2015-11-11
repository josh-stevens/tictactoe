package net.joshstevens.tictactoe.resources;

import net.joshstevens.tictactoe.models.Game;
import net.joshstevens.tictactoe.models.Move;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by joshstevens on 11/8/15.
 *
 * This file describes the "/boards" endpoint.
 *
 * A user can GET "/boards" which will show a list of boards.
 * A user can POST to "/boards" which will create a new board.
 * A user can GET "/boards/{gameID}" which will show them that board and tell them whose turn it is.
 *
 */

@Path("/boards")
public class BoardsResource {

    @Context
    private UriInfo uriInfo;
    private List<Game> games = new ArrayList<Game>();
    private AtomicInteger newId = new AtomicInteger();

    @GET
    @Produces("application/json")
    public List<String> getBoards() {
        if (games.size() == 0) {
            return Collections.singletonList("No games exist. Send a POST to this endpoint to create one.");
        }
        return games.stream()
                .map(game -> new GameResource(game, uriInfo).getUri().toString())
                .collect(Collectors.toList());
    }

    @POST
    public Response createBoard() {
        // Create new game board
        int id = newId.incrementAndGet();
        Game game = new Game(id);
        games.add(game);
        URI gameURI = new GameResource(game, uriInfo).getUri();
        return Response.created(gameURI).entity("Game created at " + gameURI.toString() + "\n").build();
    }

    @Path("{id}")
    @GET
    @Produces("text/plain")
    public String getGame(@PathParam("id") int id) {
        StringBuilder board = new StringBuilder("Game ID does not exist.\n");
        if (id-1 >= games.size()) return board.toString();

        Game reqGame = games.get(id-1);

        /* Game exists, construct response string. First, delete the "Game ID does not exist.\n" string.
         * Then, call parseBoard(), and convert StringBuilder to String. */

        return board.delete(0,24).append(reqGame.parseBoard()).toString();
    }

    @Path("{id}")
    @POST
    @Consumes("application/json")
    public Response attemptMove(Move moveRequest, @PathParam("id") int id) {
        /*
           A lot of exceptions to this function. Probably should be written as custom exceptions to
           look cleaner? Have to first check that the game exists, then check that the game is still
           in progress, then make sure the specified move is in bounds, then make sure that the square
           isn't already occupied. Worst of all, if they format the POST request incorrectly, they get
           a really awful 400 response that I need to catch somehow.
        */
        Response r;
        if (id-1 >= games.size()) {
            r = Response.status(404).entity("Game ID does not exist.\n").build();
        } else {
            Game reqGame = games.get(id-1);
            if (!reqGame.checkProgress()) {
                r = Response.status(409).entity("\nThe game is over!\n\n" + reqGame.parseBoard()).build();
            } else {
                try {
                    int row = moveRequest.row;
                    int col = moveRequest.col;

                    if (reqGame.checkSquare(row, col)) {
                        reqGame.makeMove(row, col);
                        r = Response.status(201).entity(reqGame.parseBoard()).build();
                    } else {
                        r = Response.status(409).entity("\nThat square is occupied.\n\n" + reqGame.parseBoard()).build();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    r = Response.status(400).entity("\nThat square is out of bounds.\n\n" + reqGame.parseBoard()).build();
                }
            }
        }
        return r;
    }
}
