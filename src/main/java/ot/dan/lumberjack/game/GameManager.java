package ot.dan.lumberjack.game;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ot.dan.lumberjack.LumberjackPlugin;

import java.util.*;

public class GameManager {

    private final @NotNull LumberjackPlugin plugin;
    private final Map<UUID, Game> games;
    private BukkitTask gameTask;

    public GameManager(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;
        this.games = new HashMap<>();
        start();
    }

    public void start() {
        gameTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> games.forEach((uuid, game) -> {
            game.tick();
        }), 0, 0);
    }

    public void add(@NotNull UUID player, @NotNull Game game) {
        this.games.put(player, game);
    }

    public void remove(@NotNull UUID player) {
        this.games.remove(player);
    }

    public void stop() {
        if(gameTask == null) return;
        gameTask.cancel();
    }

    public @Nullable Game get(@NotNull UUID player) {
        return this.games.get(player);
    }
}
