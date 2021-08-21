package ot.dan.lumberjack.event.implementation;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.event.BukkitEvent;
import ot.dan.lumberjack.game.Game;
import ot.dan.lumberjack.game.type.GameState;

public class GameStateChangeEvent extends BukkitEvent {

    private @NotNull final Player player;
    private @NotNull final Game game;
    private @NotNull final GameState previousState;
    private @NotNull final GameState currentState;

    public GameStateChangeEvent(@NotNull Player player, @NotNull Game game, @NotNull GameState previousState, @NotNull GameState currentState) {
        this.player = player;
        this.game = game;
        this.previousState = previousState;
        this.currentState = currentState;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull Game getGame() {
        return game;
    }

    public @NotNull GameState getPreviousState() {
        return previousState;
    }

    public @NotNull GameState getCurrentState() {
        return currentState;
    }
}
