package net.cherryflavor.api.spigot.interfaces;

import net.cherryflavor.api.game.Game;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    public String lobbyName;

    public Game game;

    public List<Player> playerList;

    public Lobby(String lobbyName) {
        this.playerList = new ArrayList<>();
        this.lobbyName = lobbyName;
    }

    public List<Player> getPlayers() { return this.playerList; }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }

    public void removePlayer(Player player) {
        this.playerList.remove(player);
    }

}
