package de.presti.animestuff.base.events.jujutsukaisen.ability;

import de.presti.animestuff.base.ability.jujutsukaisen.Domain;
import de.presti.animestuff.base.events.base.AbilityUsageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DomainAbilityUsageEvent extends AbilityUsageEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Domain domain;

    public DomainAbilityUsageEvent(Player player) {
        super(player);
        this.domain = Domain.UNFINISHED;
    }

    public DomainAbilityUsageEvent(Player player, Domain domain) {
        super(player);
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
