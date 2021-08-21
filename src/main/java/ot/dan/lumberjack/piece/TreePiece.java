package ot.dan.lumberjack.piece;

import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.piece.type.Position;

import java.util.Objects;

public class TreePiece {

    private final @NotNull ArmorStand gameObject;
    private final @NotNull Position position;

    public TreePiece(@NotNull ArmorStand gameObject, @NotNull Position position) {
        this.gameObject = gameObject;
        this.position = position;

        this.gameObject.setGravity(false);
        this.gameObject.setMarker(true);
        this.gameObject.setVisible(false);
//        this.gameObject.setFireTicks(10000);
        this.gameObject.setVisualFire(true);

        switch (position) {
            case RIGHT -> Objects.requireNonNull(this.gameObject.getEquipment()).setHelmet(LumberjackPlugin.INSTANCE.getTextureManager().getRightBranch().getTextureItem());
            case LEFT -> Objects.requireNonNull(this.gameObject.getEquipment()).setHelmet(LumberjackPlugin.INSTANCE.getTextureManager().getLeftBranch().getTextureItem());
            case NONE -> Objects.requireNonNull(this.gameObject.getEquipment()).setHelmet(LumberjackPlugin.INSTANCE.getTextureManager().getTree().getTextureItem());
        }
    }

    public void remove() {
        this.gameObject.remove();
    }

    public @NotNull ArmorStand getGameObject() {
        return gameObject;
    }

    public @NotNull Position getPiecePosition() {
        return position;
    }
}
