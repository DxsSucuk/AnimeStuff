package de.presti.animestuff.cmd;

import com.cryptomorin.xseries.XPotion;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sun.tools.javac.Main;
import de.presti.animestuff.AnimeStuff;
import de.presti.animestuff.jujutsukaisen.DomainExpansion;
import de.presti.animestuff.jujutsukaisen.DomainPreset;
import de.presti.animestuff.utils.SchematicUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TestCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(commandSender instanceof Player p)) {
            AnimeStuff.getInstance().getLogger().warning("Please execute as player entity. /");
            return true;
        }

        if(args.length != 2) return false;

        Player t = Bukkit.getPlayer(args[0]);
        if(t == null) {
            p.sendMessage("§c" + args[0] + " ist entweder nicht online oder du kannst nicht schreiben. Idiot.");
            return true;
        }
        //if(p.getUniqueId().equals(t.getUniqueId()))

        DomainPreset domainPreset;
        try {
            domainPreset = DomainPreset.valueOf(args[1].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cDie Domain " + args[1].toUpperCase(Locale.ROOT) + " existiert nicht.");
            return true;
        }
        DomainExpansion domainExpansion = new DomainExpansion(p, Set.of(t), domainPreset);

        if(DomainExpansion.REGISTRY.stream().anyMatch(dex -> dex.isOccupyingPlayer(p) || dex.isOccupyingPlayer(t))) {
            p.sendMessage("§cDu oder dein Ziel ist bereits in einer Domain.");
            return true;
        }

        domainExpansion.start();

        //p.sendMessage("§aViel Spaß mit deiner Domain huso");

        return true;
    }
}
