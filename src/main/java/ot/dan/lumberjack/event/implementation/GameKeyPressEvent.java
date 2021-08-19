package ot.dan.lumberjack.event.implementation;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.event.BukkitEvent;
import ot.dan.lumberjack.event.implementation.type.KeyPress;

import java.util.Set;

public class GameKeyPressEvent extends BukkitEvent {

    private @NotNull final Player player;
    private @NotNull final Set<KeyPress> keysPressed;

    public GameKeyPressEvent(@NotNull Player player, @NotNull Set<KeyPress> keysPressed) {
        this.player = player;
        this.keysPressed = keysPressed;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull Set<KeyPress> getKeysPressed() {
        return this.keysPressed;
    }
}
