package net.cherryflavor.api.game;

import net.cherryflavor.api.exceptions.UnserializeObjectException;

/**
 * Created on 3/12/2021
 * Time 8:16 PM
 */

public class GameSerializer {

    private Game game;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    public String serialize(Game game) {
        this.game = game;
        return game.getName() + "/" + game.getDescription().replace(' ', '_') + "/"  + game.getVersion();
    }

    public Game unserialize(String serialized) {
        String[] div = serialized.split("/");
        String gameName = div[0];
        String description = div[1];
        String version = div[2];

        Game game = null;

        try {
            game = new Game() {
                @Override
                public String getDescription() {
                    return description;
                }

                @Override
                public String getName() {
                    return gameName;
                }

                @Override
                public String getVersion() {
                    return version;
                }

                @Override
                public Game getGame() {
                    return this;
                }
            };
        } catch (UnserializeObjectException e) {
            e.printStackTrace();
        }

        return game;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}
