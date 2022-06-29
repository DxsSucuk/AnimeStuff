package de.presti.animestuff.command;

import de.presti.animestuff.AnimeStuff;
import de.presti.animestuff.base.ability.jjk.domain.DomainExpansion;
import de.presti.animestuff.base.ability.jjk.domain.DomainPreset;
import de.presti.animestuff.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Set;

public class TestCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(commandSender instanceof Player p)) {
            AnimeStuff.getInstance().getLogger().warning("Please execute as player entity.");
            return true;
        }

        DomainPreset domainPreset;
        try {
            domainPreset = DomainPreset.valueOf(args[1].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cDie Domain " + args[1].toUpperCase(Locale.ROOT) + " existiert nicht.");
            return true;
        }

        Set<Player> targets = p.getNearbyEntities(5, 5, 5)
                .stream()
                .filter(entity -> entity instanceof Player player &&
                        player != p &&
                        !PlayerUtil.isOccupied(player))
                .map(entity -> (Player) entity)
                .collect(java.util.stream.Collectors.toSet());

        DomainExpansion domainExpansion = new DomainExpansion(p, targets, domainPreset);

        if (PlayerUtil.isOccupied(p)) {
            p.sendMessage("§cDu kannst nur eine Domain auf einen Spieler auf einmal expandieren.");
        } else {
            domainExpansion.start();
        }
        return true;
    }
}
