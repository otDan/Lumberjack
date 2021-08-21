package ot.dan.lumberjack.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.BukkitEvent;
import ot.dan.lumberjack.event.implementation.GameKeyPressEvent;
import ot.dan.lumberjack.event.implementation.type.KeyPress;
import ot.dan.lumberjack.game.Game;
import ot.dan.lumberjack.util.Color;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class GeneralEvents implements Listener {

    private final @NotNull LumberjackPlugin plugin;
    private final @NotNull Set<UUID> registeredMove;
    private final @NotNull Set<UUID> registeredScroll;
    private final @NotNull Set<UUID> registeredClick;

    public GeneralEvents(@NotNull LumberjackPlugin plugin) {
        this.plugin = plugin;
        this.registeredMove = new HashSet<>();
        this.registeredScroll = new HashSet<>();
        this.registeredClick = new HashSet<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game != null) return;

        player.teleport(plugin.getGameManager().getSpawnLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(registeredMove.contains(player.getUniqueId())) return;
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
        else if(destination.getY() < from.getY()) {
            keyPresses.add(KeyPress.KEY_SHIFT);
        }

        if(player.isSprinting()) {
            keyPresses.add(KeyPress.KEY_SPRINT);
        }

        registeredMove.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> registeredMove.remove(player.getUniqueId()), 6);

        event.setCancelled(true);
        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(registeredClick.contains(player.getUniqueId())) return;
        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        Set<KeyPress> keyPresses = new HashSet<>();

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            keyPresses.add(KeyPress.KEY_RIGHT);
        }
        else if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            keyPresses.add(KeyPress.KEY_LEFT);
        }

        if(player.getLocation().getYaw() != 180) {
            player.getLocation().setYaw(180);
        }

        registeredClick.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> registeredClick.remove(player.getUniqueId()), 3);

        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerClickHologram(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof ArmorStand armorStand) {
            if(Objects.equals(armorStand.getCustomName(), Color.translate("&b&lJoin Game"))) {
                plugin.getGameManager().add(event.getPlayer());
            }
        }
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

        event.setCancelled(true);
        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        Set<KeyPress> keyPresses = new HashSet<>();
        keyPresses.add(KeyPress.KEY_DROP);

        if(player.getLocation().getYaw() != 180) {
            player.getLocation().setYaw(180);
        }

        registeredClick.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> registeredClick.remove(player.getUniqueId()), 5);

        event.setCancelled(true);
        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }

    @EventHandler
    public void onPlayerScroll(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if(event.getNewSlot() == 4) return;
        if(registeredScroll.contains(player.getUniqueId())) return;
        Game game = plugin.getGameManager().get(player.getUniqueId());
        if(game == null) return;

        Set<KeyPress> keyPresses = new HashSet<>();

        int previous = event.getPreviousSlot();
        int next = event.getNewSlot();

        if(previous > next) {
            keyPresses.add(KeyPress.KEY_MOUSE_UP);
        }
        else if(previous < next) {
            keyPresses.add(KeyPress.KEY_MOUSE_DOWN);
        }

        if(player.getLocation().getYaw() != 180) {
            player.getLocation().setYaw(180);
        }

        registeredScroll.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> registeredScroll.remove(player.getUniqueId()), 5);

        BukkitEvent.callEvent(new GameKeyPressEvent(player, keyPresses));
    }
}
