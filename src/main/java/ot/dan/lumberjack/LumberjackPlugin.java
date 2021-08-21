package ot.dan.lumberjack;

import org.bukkit.plugin.java.JavaPlugin;
import ot.dan.lumberjack.command.implementation.LumberjackCommand;
import ot.dan.lumberjack.game.GameManager;
import ot.dan.lumberjack.listener.GameEvents;
import ot.dan.lumberjack.listener.GeneralEvents;
import ot.dan.lumberjack.texture.TextureManager;

public final class LumberjackPlugin extends JavaPlugin {

    public static LumberjackPlugin INSTANCE;
    private GameManager gameManager;
    private TextureManager textureManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.gameManager = new GameManager(this);
        this.textureManager = new TextureManager();

        new GeneralEvents(this);
        new GameEvents(this);

        new LumberjackCommand(this, "lumberjack");
    }

    @Override
    public void onDisable() {
        this.gameManager.stop();
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }
}
