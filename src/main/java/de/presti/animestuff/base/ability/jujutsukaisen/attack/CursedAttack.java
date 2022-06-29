package de.presti.animestuff.base.ability.jujutsukaisen.attack;

import de.presti.animestuff.AnimeStuff;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class CursedAttack {

    private final String name;
    private final float neededEnergy;
    private final AttackStyle attackStyle;

    public CursedAttack(String name, float neededEnergy, AttackStyle attackStyle) {
        this.name = name;
        this.neededEnergy = neededEnergy;
        this.attackStyle = attackStyle;
    }

    public void execute(Player player,  Player target) {
        AnimeStuff.getInstance().getLogger().severe("Executing Attack " + name + " is not possible on single Target, since it is not implemented!");
    }

    public void execute(Player player, Location location) {
        AnimeStuff.getInstance().getLogger().severe("Executing Attack " + name + " is not possible on a Location, since it is not implemented!");
    }

    public void execute(Player player, Set<Player> targets) {
        AnimeStuff.getInstance().getLogger().severe("Executing Attack " + name + " is not possible on multiple Targets, since it is not implemented!");
    }

    public String getName() {
        return name;
    }

    public float getNeededEnergy() {
        return neededEnergy;
    }

    public AttackStyle getAttackStyle() {
        return attackStyle;
    }
}
