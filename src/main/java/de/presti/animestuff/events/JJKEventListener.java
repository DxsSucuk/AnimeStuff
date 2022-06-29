package de.presti.animestuff.events;

import de.presti.animestuff.AnimeStuff;
import de.presti.animestuff.base.events.jujutsukaisen.DomainCreationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JJKEventListener implements Listener {

    @EventHandler
    public void onDomainCreationEvent(DomainCreationEvent event) {
        AnimeStuff.getInstance().getLogger().info("The Player " + event.getCreator().getName() + " created a new Domain: " + event.getDomain().getTitle());
    }

}
