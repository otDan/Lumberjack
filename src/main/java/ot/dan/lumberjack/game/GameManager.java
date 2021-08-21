package ot.dan.lumberjack.game;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TouchableLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.implementation.type.KeyPress;
import ot.dan.lumberjack.util.Color;

import java.util.*;

public class GameManager {

    private final @NotNull LumberjackPlugin plugin;
    private final Map<UUID, Game> games;
    private BukkitTask gameTask;
    private final @NotNull Location spawnLocation, initialLocation;
    private int offset;

    private final @NotNull Hologram joinHologram;

    public GameManager(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;
        this.games = new HashMap<>();
        this.spawnLocation = new Location(plugin.getServer().getWorld("world"), 0.5, 0, 0.5);
        this.initialLocation = this.spawnLocation.clone();

        this.joinHologram = HologramsAPI.createHologram(plugin, this.initialLocation.clone().add(0, 3, 5));
        this.joinHologram.appendTextLine(Color.translate("Join Game:"));
        this.joinHologram.appendTextLine(Color.translate("&b&lLumberjack"));
        HologramLine hologramLine = this.joinHologram.getLine(0);
        TouchableLine touchableLine = (TouchableLine) hologramLine;
        touchableLine.setTouchHandler(player -> {
            plugin.getGameManager().add(player);
        });

        this.initialLocation.add(100, 0, -100);
        this.offset = 0;
        start();
    }

    public void start() {
        gameTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> games.forEach((uuid, game) -> {
            game.tick();
        }), 0, 0);
    }

    public void add(@NotNull Player player) {
        if(this.games.containsKey(player.getUniqueId())) return;

        Location gameLocation = initialLocation.clone();
        gameLocation.add(offset, 0, 0);
        offset += 10;
        this.games.put(player.getUniqueId(), new Game(player, gameLocation));
    }

    public void remove(@NotNull UUID player) {
        this.games.remove(player);
    }

    public void stop() {
        if(gameTask == null) return;
        gameTask.cancel();
        games.forEach((uuid, game) -> game.stop());
    }

    public void control(@NotNull UUID player, @NotNull Set<KeyPress> pressedKeys) {
        Game game = get(player);
        if(game == null) return;
        game.control(pressedKeys);
    }

    public @Nullable Game get(@NotNull UUID player) {
        return this.games.get(player);
    }

    public @NotNull Location getSpawnLocation() {
        Location out = spawnLocation.clone();
        out.setY(2);
        return out;
    }
}
