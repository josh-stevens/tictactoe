package net.joshstevens.tictactoe.resources;

import net.joshstevens.tictactoe.models.Game;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by joshstevens on 11/8/15.
 *
 * This represents a specific game resource.
 */
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {
    private final Game game;
    private final UriInfo uriInfo;
    public String currentPlayer;

    public GameResource(Game game, UriInfo uriInfo) {
        this.game = game;
        this.uriInfo = uriInfo;
        this.currentPlayer = "X";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Game getGame() {
        return game;
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().path(Integer.toString(game.getId())).build();
    }
}
