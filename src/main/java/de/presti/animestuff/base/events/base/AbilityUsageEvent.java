package de.presti.animestuff.base.events.base;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbilityUsageEvent extends Event {

    private final Player player;

    public AbilityUsageEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
