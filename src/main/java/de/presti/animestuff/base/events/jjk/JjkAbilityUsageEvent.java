package de.presti.animestuff.base.events.jjk;

import de.presti.animestuff.base.events.base.AbilityUsageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class JjkAbilityUsageEvent extends AbilityUsageEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private float cursedEnergy = 0f;
    private boolean cancelled;

    public JjkAbilityUsageEvent(@Nonnull Player player) {
        super(player);
    }

    public JjkAbilityUsageEvent(@Nonnull Player player, float cursedEnergy) {
        super(player);
        this.cursedEnergy = cursedEnergy;
    }

    public float getCursedEnergy() {
        return cursedEnergy;
    }

    public void setCursedEnergy(float cursedEnergy) {
        this.cursedEnergy = cursedEnergy;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
