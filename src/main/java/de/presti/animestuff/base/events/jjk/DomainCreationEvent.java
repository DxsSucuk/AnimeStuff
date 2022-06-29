package de.presti.animestuff.base.events.jjk;

import de.presti.animestuff.base.ability.jjk.domain.DomainExpansion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DomainCreationEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player creator;
    private final Location domainLocation;
    private final DomainExpansion domain;
    private boolean cancelled;

    public DomainCreationEvent(Player creator, Location domainLocation, DomainExpansion domain) {
        this.creator = creator;
        this.domainLocation = domainLocation;
        this.domain = domain;
    }

    public Player getCreator() {
        return creator;
    }

    public Location getDomainLocation() {
        return domainLocation;
    }

    public DomainExpansion getDomain() {
        return domain;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
