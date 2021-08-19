package ot.dan.lumberjack.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import ot.dan.lumberjack.LumberjackPlugin;

import java.util.List;
import java.util.Objects;

public abstract class AbstractCommand implements Command, CommandExecutor, TabCompleter {

    private CommandSender commandSender;
    private final String name;
//    private final TabCompletes tabCompletes;

    public AbstractCommand(@NotNull LumberjackPlugin plugin, String command) {
        this.name = command;
        PluginCommand pluginCommand = Objects.requireNonNull(plugin.getCommand(this.name));
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    @Override
    public CommandSender getCommandSender() {
        return this.commandSender;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args) {
        this.commandSender = commandSender;
        this.perform(commandSender, command, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args) {
        this.commandSender = sender;
        return complete(sender, command, label, args);
    }

    public abstract void perform(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);

    public abstract List<String> complete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);
}

