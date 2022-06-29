package de.presti.animestuff.base.events.jujutsukaisen;

import de.presti.animestuff.base.ability.jujutsukaisen.domain.DomainExpansion;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DomainAbilityUsageEvent extends JujutsukaisenAbilityUsageEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final DomainExpansion domain;

    private boolean cancelled;

    public DomainAbilityUsageEvent(Player player) {
        super(player, 0f);
        this.domain = null;
    }

    public DomainAbilityUsageEvent(Player player, DomainExpansion domain) {
        super(player, 0f);
        this.domain = domain;
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
