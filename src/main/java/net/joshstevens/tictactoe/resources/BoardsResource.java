package net.joshstevens.tictactoe.resources;

import net.joshstevens.tictactoe.models.Game;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getBoards() {
        return games.stream()
                .map(game -> new GameResource(game, uriInfo))
                .map(GameResource::getUri)
                .map(URI::toString)
                .collect(Collectors.toList());
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String getGame(@PathParam("id") int id) {
        GameResource reqGame = games.stream()
                                .filter(game -> id == game.getId())
                                .findFirst()
                                .map(game -> new GameResource(game, uriInfo))
                                .orElse(null);
        if (reqGame == null) return "Game ID does not exist.\n";
        return " | | \n-----\n | | \n-----\n | | \nNext player: " + reqGame.currentPlayer + "\n";

    }
}
