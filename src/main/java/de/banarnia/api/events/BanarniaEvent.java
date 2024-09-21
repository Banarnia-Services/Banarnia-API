package de.banarnia.api.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class BanarniaEvent extends Event {

    /**
     * Call the event.
     */
    public void callEvent() {
        if (!Bukkit.isPrimaryThread())
            Bukkit.getLogger().warning("Calling event from async thread may not work: " + this.getClass().getName());

        Bukkit.getPluginManager().callEvent(this);
    }

    // Bukkit shit.
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
