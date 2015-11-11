package net.joshstevens.tictactoe;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import net.joshstevens.tictactoe.resources.BoardsResource;

/**
 * Created by joshstevens on 11/8/15.
 */
public class GameService extends Service<GameConfiguration> {

    public static void main(String[] args) throws Exception {
        new GameService().run(args);
    }

    @Override
    public void initialize(Bootstrap<GameConfiguration> bootstrap) {

    }

    @Override
    public void run(GameConfiguration gameConfiguration, Environment environment) throws Exception {
        environment.addResource(new BoardsResource());
    }
}
