package ot.dan.lumberjack.command;

import org.bukkit.command.CommandSender;

public interface Command {

    CommandSender getCommandSender();

    String getName();
}
