package ot.dan.lumberjack.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.implementation.GameKeyPressEvent;
import ot.dan.lumberjack.event.implementation.GameStateChangeEvent;
import ot.dan.lumberjack.game.Game;
import ot.dan.lumberjack.util.Color;

public class GameEvents implements Listener {

    private final @NotNull LumberjackPlugin plugin;

    public GameEvents(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameKeyPress(GameKeyPressEvent event) {
        Player player = event.getPlayer();
        this.plugin.getGameManager().control(player.getUniqueId(), event.getKeysPressed());
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event) {
        Player player = event.getPlayer();
        Game game = event.getGame();

        switch (event.getCurrentState()) {
            case STARTING -> {
                player.sendTitle(Color.translate("&b&lStarting"), "", 0, 25, 0);
            }
            case STARTED -> {
                player.sendTitle(Color.translate("&b&lStarted"), "", 0, 25, 0);
            }
            case ENDING -> {
                this.plugin.getGameManager().remove(player.getUniqueId());
                player.getInventory().clear();
                player.setAllowFlight(false);
                player.setWalkSpeed(0.2f);
                player.setFlySpeed(0.1f);
                player.teleport(plugin.getGameManager().getSpawnLocation());
                player.sendTitle(Color.translate("&b&lGame Over"), Color.translate("Height: &b" + game.getHeight() + " &fScore: &b" + game.getScore()), 0, 25, 0);
            }
        }
    }

    @EventHandler
    public void TridentThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) return;

        Game game = this.plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        if (!(event.getEntity() instanceof Trident)) return;

        event.setCancelled(true);
    }
}
