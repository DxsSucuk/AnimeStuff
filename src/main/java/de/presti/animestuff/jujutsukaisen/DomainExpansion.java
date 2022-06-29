package de.presti.animestuff.jujutsukaisen;

import com.cryptomorin.xseries.XPotion;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import de.presti.animestuff.utils.SchematicUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DomainExpansion {

    public static List<DomainExpansion> REGISTRY;

    public final Player caster;
    public final Set<Player> targets;
    public final String title;
    public final String schemPath;
    public final Title ingameTitle;

    private Clipboard schemClipboard;
    private boolean loopsAround;

    public DomainExpansion(Player caster, Set<Player> targets, DomainPreset domainPreset) {
        if(REGISTRY == null)
            REGISTRY = new ArrayList<>();

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

        REGISTRY.add(this);
    }

    public void start() {
        prepare();
        phaseBegin();
        SchematicUtil.pasteSchematic(schemClipboard, caster, caster.getLocation());     // Location temporär, später maybe im Himmel oder so.
        // TODO: Wenn Location nicht beim Caster ist, noch teleportieren.

        if(loopsAround)
            // TODO: Player Movement Event einrichten, damit Spieler in der Domain loopen wenn sie rumlaufen.
        {}

        // TODO: Vielleicht Buffs?
    }

    private void prepare() {
        Clipboard clipboard = SchematicUtil.loadSchematic(schemPath);
        if(clipboard == null) {
            caster.sendMessage("§cSchematic für diese Domain existiert nicht, kontaktiere einen Admin.");
            return;
        }

        schemClipboard = clipboard;
    }
    private void phaseBegin() {
        List<String> darkness = List.of("DARKNESS");

        caster.showTitle(ingameTitle);
        XPotion.addEffects(caster, darkness);
        targets.forEach(t -> {
            t.showTitle(ingameTitle);
            XPotion.addEffects(t, darkness);
        });
    }

    public boolean isOccupyingPlayer(@NotNull Player player) {
        if(caster.getUniqueId().equals(player.getUniqueId())) return true;
        return targets.stream().anyMatch(t -> t.getUniqueId().equals(player.getUniqueId()));
    }

}
