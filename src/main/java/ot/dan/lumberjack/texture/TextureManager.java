package ot.dan.lumberjack.texture;

import org.jetbrains.annotations.NotNull;

public class TextureManager {

    private final @NotNull Texture character;
    private final @NotNull Texture tree;
    private final @NotNull Texture rightBranch;
    private final @NotNull Texture leftBranch;
    private final @NotNull Texture blank;

    public TextureManager() {
        this.character = new Texture(1);
        this.tree = new Texture(2);
        this.rightBranch = new Texture(3);
        this.leftBranch = new Texture(4);
        this.blank = new Texture(1000);
    }

    public @NotNull Texture getCharacter() {
        return character;
    }

    public @NotNull Texture getTree() {
        return tree;
    }

    public @NotNull Texture getRightBranch() {
        return rightBranch;
    }

    public @NotNull Texture getLeftBranch() {
        return leftBranch;
    }

    public @NotNull Texture getBlank() {
        return blank;
    }
}
