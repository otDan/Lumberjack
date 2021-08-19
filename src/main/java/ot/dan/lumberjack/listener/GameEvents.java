package ot.dan.lumberjack.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.implementation.GameKeyPressEvent;

public class GameEvents implements Listener {

    private final @NotNull LumberjackPlugin plugin;

    public GameEvents(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameMove(GameKeyPressEvent event) {
        event.getKeysPressed().forEach(keyPress -> Bukkit.broadcastMessage(keyPress.name()));
    }
}
