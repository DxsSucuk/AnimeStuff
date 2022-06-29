package de.presti.animestuff.base.events.jujutsukaisen.ability;

import de.presti.animestuff.base.ability.jujutsukaisen.domain.DomainExpansion;
import de.presti.animestuff.base.events.base.AbilityUsageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DomainAbilityUsageEvent extends AbilityUsageEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final DomainExpansion domain;

    public DomainAbilityUsageEvent(Player player) {
        super(player);
        this.domain = null;
    }

    public DomainAbilityUsageEvent(Player player, DomainExpansion domain) {
        super(player);
        this.domain = domain;
    }

    public DomainExpansion getDomain() {
        return domain;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
