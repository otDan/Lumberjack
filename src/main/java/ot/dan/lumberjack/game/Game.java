package ot.dan.lumberjack.game;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.event.BukkitEvent;
import ot.dan.lumberjack.event.implementation.GameStateChangeEvent;
import ot.dan.lumberjack.event.implementation.type.KeyPress;
import ot.dan.lumberjack.game.type.GameState;
import ot.dan.lumberjack.piece.TreePiece;
import ot.dan.lumberjack.piece.type.Position;
import ot.dan.lumberjack.util.Color;

import java.util.*;

public class Game {

    private final @NotNull UUID player;
    private @NotNull GameState gameState;
    private final @NotNull Location location;
    private int tick;
    private int count;

    private int height;
    private int score;

    private @NotNull Position characterPosition;
    private final @NotNull ArmorStand character;
    private final @NotNull List<TreePiece> tree;
    private int parts;

    public Game(@NotNull Player player, @NotNull Location location) {
        this.player = player.getUniqueId();
        player.setFlySpeed(0.0245f);
        player.setWalkSpeed(0.0245f);
        ItemStack blank = LumberjackPlugin.INSTANCE.getTextureManager().getBlank().getTextureItem();
        for (int i = 0; i < 36; i++) {
            player.getInventory().setItem(i, blank);
        }

        this.location = location.getBlock().getLocation().clone().add(0.5, 0, 0.5);
        this.location.setY(0);
        this.location.setYaw(0);
        this.location.setPitch(0);
        this.tick = 0;
        this.count = 5*20;

        this.height = 0;
        this.score = 0;
        this.characterPosition = Position.RIGHT;

        Location characterLocation = this.location.clone();
        characterLocation.add(1, 0, -4);
        this.character = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(characterLocation, EntityType.ARMOR_STAND);
        this.character.setGravity(false);
        this.character.setMarker(true);
        this.character.setVisible(false);
        Objects.requireNonNull(this.character.getEquipment()).setHelmet(LumberjackPlugin.INSTANCE.getTextureManager().getCharacter().getTextureItem());

        this.tree = new ArrayList<>();
        this.parts = 0;

        this.gameState = GameState.STARTING;
        prepare();
    }

    public void start() {
        BukkitEvent.callEvent(new GameStateChangeEvent(getPlayer(), this, this.gameState, GameState.STARTED));
        this.gameState = GameState.STARTED;
    }

    public void tick() {
        Player player = getPlayer();
        if(player == null) return;

        if(player.getInventory().getHeldItemSlot() != 4) {
            player.getInventory().setHeldItemSlot(4);
        }

        if(!player.isFlying()) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }

        Location characterLocation = this.character.getLocation().clone();
        characterLocation.setY(this.location.getY() + this.height);
        switch (this.gameState) {
            case STARTING -> {
                playerPosition(player);
                managePosition(characterLocation);
                countdown();
            }
            case STARTED -> {
                playerPosition(player);
                managePosition(characterLocation);
            }
            case ENDING -> {
            }
        }
    }

    private void playerPosition(Player player) {
        if(this.tick >= 4) {
            Location gameLocation = location.clone();
            gameLocation.setY(gameLocation.getY() + height);
            gameLocation.setYaw(180);
            gameLocation.setPitch(0);
            if (gameLocation != player.getLocation()) {
                player.teleport(gameLocation);
            }
            this.tick = 0;
        }
        else {
            this.tick++;
        }
    }

    private void managePosition(Location characterLocation) {
        switch (this.characterPosition) {
            case RIGHT -> characterLocation.setX(this.location.getX() + 1);
            case LEFT -> characterLocation.setX(this.location.getX() - 1);
            case NONE -> characterLocation.setX(this.location.getX());
        }
        this.character.teleport(characterLocation);
    }

    public void countdown() {
        if(this.count == 0) {
            start();
        }
        else {
            if(this.count % 20 == 0) {
                int time = this.count / 20;
                getPlayer().sendTitle(Color.translate("&b&lStarting in:"), Color.translate("" + time), 0, 25, 0);
            }
            this.count -= 1;
        }
    }

    public void stop() {
        BukkitEvent.callEvent(new GameStateChangeEvent(getPlayer(), this, this.gameState, GameState.ENDING));
        this.gameState = GameState.ENDING;
        this.character.remove();
        this.tree.forEach(treePiece -> treePiece.getGameObject().remove());
        this.tree.clear();
    }

    public void control(Set<KeyPress> pressedKeys) {
        switch (this.gameState) {
            case STARTING -> {
//                for (KeyPress pressedKey:pressedKeys) {
//                    switch (pressedKey) {
//                        case KEY_W -> {
//                        }
//                        case KEY_A -> {
//                        }
//                        case KEY_S -> {
//                        }
//                        case KEY_D -> {
//                        }
//                        case KEY_DROP -> {
//                        }
//                        case KEY_JUMP -> {
//                        }
//                        case KEY_SHIFT -> {
//                        }
//                        case KEY_OFFHAND -> {
//                        }
//                        case KEY_RIGHT -> {
//                        }
//                        case KEY_LEFT -> {
//                        }
//                        case KEY_MOUSE_UP -> {
//                        }
//                        case KEY_MOUSE_DOWN -> {
//                        }
//                    }
//                }
            }
            case STARTED -> {
                for (KeyPress pressedKey : pressedKeys) {
                    switch (pressedKey) {
                        case KEY_W -> {
//                            Bukkit.broadcastMessage("W");
                        }
                        case KEY_A -> {
//                            Bukkit.broadcastMessage("A");
                            this.characterPosition = Position.LEFT;
                            handleBreak();
                        }
                        case KEY_S -> {
//                            Bukkit.broadcastMessage("S");
                        }
                        case KEY_D -> {
//                            Bukkit.broadcastMessage("D");
                            this.characterPosition = Position.RIGHT;
                            handleBreak();
                        }
                        case KEY_DROP -> {
//                            Bukkit.broadcastMessage("DROP");
                        }
                        case KEY_SPRINT -> {
//                            Bukkit.broadcastMessage("SPRINT");
                        }
                        case KEY_JUMP -> {
//                            Bukkit.broadcastMessage("JUMP");
                        }
                        case KEY_SHIFT -> {
//                            Bukkit.broadcastMessage("SHIFT");
                        }
                        case KEY_OFFHAND -> {
//                            Bukkit.broadcastMessage("OFFHAND");
                        }
                        case KEY_RIGHT -> {
//                            Bukkit.broadcastMessage("RIGHT");
                            this.characterPosition = Position.RIGHT;
                            handleBreak();
                        }
                        case KEY_LEFT -> {
//                            Bukkit.broadcastMessage("LEFT");
                            this.characterPosition = Position.LEFT;
                            handleBreak();
                        }
                        case KEY_MOUSE_UP -> {
//                            Bukkit.broadcastMessage("MOUSE_UP");
                        }
                        case KEY_MOUSE_DOWN -> {
//                            Bukkit.broadcastMessage("MOUSE_DOWN");
                        }
                    }
                }
            }
            case ENDING -> {
//                for (KeyPress pressedKey:pressedKeys) {
//                    switch (pressedKey) {
//                        case KEY_W -> {
//                        }
//                        case KEY_A -> {
//                        }
//                        case KEY_S -> {
//                        }
//                        case KEY_D -> {
//                        }
//                        case KEY_DROP -> {
//                        }
//                        case KEY_JUMP -> {
//                        }
//                        case KEY_SHIFT -> {
//                        }
//                        case KEY_OFFHAND -> {
//                        }
//                        case KEY_RIGHT -> {
//                        }
//                        case KEY_LEFT -> {
//                        }
//                        case KEY_MOUSE_UP -> {
//                        }
//                        case KEY_MOUSE_DOWN -> {
//                        }
//                    }
//                }
//            }
            }
        }
    }

    private void prepare() {
        for (int i = 0; i < 5; i++) {
            addBlankPiece();
        }
        for (int i = 0; i < 15; i++) {
            addRandomPiece();
        }
    }

    private void addBlankPiece() {
        Location initialLocation = this.location.clone();
        initialLocation.add(0, this.parts, -4);
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(initialLocation.getWorld()).spawnEntity(initialLocation, EntityType.ARMOR_STAND);
        TreePiece treePiece = new TreePiece(armorStand, Position.NONE);
        this.tree.add(treePiece);
        this.parts++;
    }

    private void addRandomPiece() {
        Location initialLocation = this.location.clone();
        initialLocation.add(0, this.parts, -4);
        this.parts++;
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(initialLocation.getWorld()).spawnEntity(initialLocation, EntityType.ARMOR_STAND);
        Position position = randomPiecePosition();
        TreePiece treePiece = new TreePiece(armorStand, position);
        this.tree.add(treePiece);

        if(position.equals(Position.NONE)) return;
        addBlankPiece();
    }

    private void handleBreak() {
        TreePiece checkPiece = this.tree.get(1);
        if(checkPiece.getPiecePosition().equals(this.characterPosition)) {
            stop();
        }
        else {
            TreePiece treePiece = this.tree.get(0);
            Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.ITEM_CRACK, treePiece.getGameObject().getLocation(), 20, 0, 0.1, 0.1, 0.1, LumberjackPlugin.INSTANCE.getTextureManager().getTree().getTextureItem());
            treePiece.remove();
            this.tree.remove(treePiece);
            if(this.tree.size() < 20) {
                addRandomPiece();
            }
            this.height++;
        }
    }

    public Position randomPiecePosition() {
        int part = getRandomNumber(1, 6);
        switch (part) {
            case 2 -> {
                return Position.RIGHT;
            }
            case 4 -> {
                return Position.LEFT;
            }
        }
        return Position.NONE;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }
}
