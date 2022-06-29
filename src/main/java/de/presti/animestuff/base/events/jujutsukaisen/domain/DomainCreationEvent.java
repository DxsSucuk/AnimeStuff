package de.presti.animestuff.base.events.jujutsukaisen.domain;

import de.presti.animestuff.base.ability.jujutsukaisen.Domain;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DomainCreationEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player creator;
    private final Location domainLocation;
    private final Domain domain;

    public DomainCreationEvent(Player creator, Location domainLocation, Domain domain) {
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

    public Domain getDomain() {
        return domain;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
