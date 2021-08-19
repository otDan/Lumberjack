package ot.dan.lumberjack.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Utility method for easy event declaration
     *
     * @param async if the event is run async
     */
    public BukkitEvent(boolean async) {
        super(async);
    }

    /**
     * Utility method for easy event declaration
     * <p>
     * Not for async events.
     */
    public BukkitEvent() {
        this(false);
    }

    /**
     * Call any class that implements {@link Event}
     *
     * @param event event to call
     */
    public static void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public final @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}

