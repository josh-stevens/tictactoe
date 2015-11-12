package net.joshstevens.tictactoe;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.joshstevens.tictactoe.resources.BoardsResource;

/**
 * Created by joshstevens on 11/8/15.
 */
public class GameService extends Application<GameConfiguration> {

    public static void main(String[] args) throws Exception {
        new GameService().run(args);
    }

    @Override
    public void initialize(Bootstrap<GameConfiguration> bootstrap) {

    }

    @Override
    public void run(GameConfiguration gameConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new BoardsResource());
    }
}
