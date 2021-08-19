package ot.dan.lumberjack.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Game {

    private final @NotNull UUID player;
    private @NotNull GameState gameState;
    private @NotNull final Location location;
    private int height;
    private int score;

    public Game(@NotNull UUID player, @NotNull Location location) {
        this.player = player;
        this.location = location;
        this.gameState = GameState.STARTING;
    }

    public void start() {
        this.gameState = GameState.STARTED;
    }

    public void tick() {
        if(!this.gameState.equals(GameState.STARTED)) return;
        Location gameLocation = location.clone();
        gameLocation.setY(height);
        getPlayer().teleport(gameLocation);
    }

    public void stop() {
        this.gameState = GameState.ENDING;
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }
}
