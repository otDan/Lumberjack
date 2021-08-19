package ot.dan.lumberjack;

import org.bukkit.plugin.java.JavaPlugin;
import ot.dan.lumberjack.command.implementation.LumberjackCommand;
import ot.dan.lumberjack.game.GameManager;
import ot.dan.lumberjack.listener.GameEvents;
import ot.dan.lumberjack.listener.GeneralEvents;

public final class LumberjackPlugin extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);

        new GeneralEvents(this);
        new GameEvents(this);

        new LumberjackCommand(this, "lumberjack");
    }

    @Override
    public void onDisable() {
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
