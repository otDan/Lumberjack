package ot.dan.lumberjack.listener;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.BukkitEvent;
import ot.dan.lumberjack.event.implementation.GameKeyPressEvent;
import ot.dan.lumberjack.event.implementation.type.KeyPress;
import ot.dan.lumberjack.game.Game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GeneralEvents implements Listener {

    private final @NotNull LumberjackPlugin plugin;
    private final @NotNull Set<UUID> registered;

    public GeneralEvents(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;
        this.registered = new HashSet<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(registered.contains(player.getUniqueId())) return;
        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        Set<KeyPress> keyPresses = new HashSet<>();
        Location from = event.getFrom();
        Location destination = event.getTo();

        assert destination != null;

        if(destination.getYaw() != 180) {
            destination.setYaw(180);
        }

        if(destination.getX() > from.getX()) {
            keyPresses.add(KeyPress.KEY_D);
        }
        else if(destination.getX() < from.getX()) {
            keyPresses.add(KeyPress.KEY_A);
        }

        if(destination.getZ() > from.getZ()) {
            keyPresses.add(KeyPress.KEY_S);
        }
        else if(destination.getZ() < from.getZ()) {
            keyPresses.add(KeyPress.KEY_W);
        }

        if(destination.getY() > from.getY()) {
            keyPresses.add(KeyPress.KEY_JUMP);
        }

        if(player.isSneaking()) {
            keyPresses.add(KeyPress.KEY_SHIFT);
        }

        registered.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> registered.remove(player.getUniqueId()), 8);

        event.setCancelled(true);
        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerShift(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        if(!event.isSneaking()) return;

        Set<KeyPress> keyPresses = new HashSet<>();
        keyPresses.add(KeyPress.KEY_SHIFT);

        if(player.getLocation().getYaw() != 180) {
            player.getLocation().setYaw(180);
        }

        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerHandSwitch(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        Set<KeyPress> keyPresses = new HashSet<>();
        keyPresses.add(KeyPress.KEY_OFFHAND);

        if(player.getLocation().getYaw() != 180) {
            player.getLocation().setYaw(180);
        }

        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }
}
