package ot.dan.lumberjack.command.implementation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;
import ot.dan.lumberjack.command.AbstractCommand;

import java.util.Collections;
import java.util.List;

public class LumberjackCommand extends AbstractCommand {

    private @NotNull final LumberjackPlugin plugin;

    public LumberjackCommand(@NotNull LumberjackPlugin plugin, String command) {
        super(plugin, command);
        this.plugin = plugin;
    }

    @Override
    public void perform(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player player) {
            plugin.getGameManager().add(player);
        }
    }

    @Override
    public List<String> complete(CommandSender commandSender, Command command, String label, String[] args) {
        return Collections.singletonList("test");
    }
}