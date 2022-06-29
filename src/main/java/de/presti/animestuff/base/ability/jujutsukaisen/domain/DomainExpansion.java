package de.presti.animestuff.base.ability.jujutsukaisen.domain;

import com.cryptomorin.xseries.XPotion;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import de.presti.animestuff.AnimeStuff;
import de.presti.animestuff.base.events.jujutsukaisen.domain.DomainCreationEvent;
import de.presti.animestuff.utils.PlayerUtil;
import de.presti.animestuff.utils.SchematicUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DomainExpansion {
    public final Player caster;
    public final Set<Player> targets;
    public final String title;
    public final String schemPath;
    public final Title ingameTitle;
    private final boolean loopsAround;

    public DomainExpansion(Player caster, Set<Player> targets, DomainPreset domainPreset) {

        this.caster = caster;
        this.targets = targets;
        this.title = domainPreset.getClearName();
        this.schemPath = domainPreset.toString().toLowerCase(Locale.ROOT) + ".schem";
        this.loopsAround = domainPreset.loopsAround();

        this.ingameTitle = Title.title(

                // Alles temporär hardcoded, Config kommt später
                Component.empty(),
                Component.text("Domain Expansion: " + title)
                         .color(TextColor.color(0xFFCEC2)),
                Title.Times.times(
                        Duration.ofMillis(500L),
                        Duration.ofMillis(3000L),
                        Duration.ofMillis(200L)
                )

        );
    }

    public void start() {
        prepare();
        phaseBegin();

        if(loopsAround)
        {
            // TODO: Player Movement Event einrichten, damit Spieler in der Domain loopen wenn sie rumlaufen. <- Statt einen external Event zu benutzen was nach einen boolean sucht, einfach ein Event erstellt was nach einen Meta Tag sucht.
        }

        // TODO: Vielleicht Buffs?
    }

    private void prepare() {
        Clipboard clipboard = SchematicUtil.loadSchematic(schemPath);
        if(clipboard == null) {
            caster.sendMessage("§cSchematic für diese Domain existiert nicht, kontaktiere einen Admin.");
            return;
        }

        Bukkit.getPluginManager().callEvent(new DomainCreationEvent(caster, caster.getLocation(), this));

        SchematicUtil.pasteSchematic(clipboard, caster, caster.getLocation());
        PlayerUtil.occupyPlayer(caster);

        // Hiding all players except caster and targets
        for (Player allNonTargets: Bukkit.getOnlinePlayers()) {
            if (targets.contains(allNonTargets) || allNonTargets == caster) {
                continue;
            }

            caster.hidePlayer(AnimeStuff.getInstance(), allNonTargets);

            for (Player all : targets) {
                all.hidePlayer(AnimeStuff.getInstance(), allNonTargets);
            }
        }

        for (Player all : targets) {
            SchematicUtil.pasteSchematic(clipboard, all, caster.getLocation());
            PlayerUtil.occupyPlayer(all);
        }

        // TODO:: add spherical Schematic with radius of 10 blocks.
        SchematicUtil.pasteSchematic(SchematicUtil.loadSchematic("domain_expansion_sphere.schem"), caster, caster.getLocation());
    }
    private void phaseBegin() {

        PotionEffect potionEffect = XPotion.DARKNESS.buildPotionEffect(20 * 5, 100);

        if (potionEffect != null) caster.addPotionEffect(potionEffect);
        caster.showTitle(ingameTitle);
        targets.forEach(t -> {
            t.showTitle(ingameTitle);
            if (potionEffect != null) t.addPotionEffect(potionEffect);
        });
    }

    public void end() {
        SchematicUtil.revertLastSchematic(caster);
        PlayerUtil.freePlayer(caster);

        for (Player all : targets) {
            SchematicUtil.revertLastSchematic(all);
            PlayerUtil.freePlayer(all);
        }

    }
}
