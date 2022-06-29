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

        /*
          TODO:: lieber nicht in eine Implementations klasse eine REGISTRY haben, statt dessen eine eigenen PlayerImpl haben welche diese Information speichert,
           vorteil davon wäre das wir die daten in der User Meta data speichern können und diese info selbst nach einem reload immernoch vorhanden ist.
        */

        /* if(DomainExpansion.REGISTRY.stream().anyMatch(dex -> dex.isOccupyingPlayer(p) || dex.isOccupyingPlayer(t))) {
            p.sendMessage("§cDu oder dein Ziel ist bereits in einer Domain.");
            return true;
        }*/

        domainExpansion.start();
        return true;
    }
}
