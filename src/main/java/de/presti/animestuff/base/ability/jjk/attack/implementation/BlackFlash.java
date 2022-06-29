package de.presti.animestuff.base.ability.jjk.attack.implementation;

import com.cryptomorin.xseries.XPotion;
import de.presti.animestuff.base.ability.jjk.attack.AttackStyle;
import de.presti.animestuff.base.ability.jjk.attack.CursedAttack;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BlackFlash extends CursedAttack {

    public BlackFlash() {
        super("Black Flash", 1f, AttackStyle.SINGLE);
    }

    @Override
    public void execute(Player player, Player target) {
        // TODO:: add check if the user has enough energy.

        PotionEffect potionEffect = XPotion.DARKNESS.buildPotionEffect(20 ,100);

        if (ThreadLocalRandom.current().nextDouble() == 0.0001D) {
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player player1) {
                    if (potionEffect != null)
                        player1.addPotionEffect(potionEffect);
                }
            }
            if (!target.isDead()) {
                target.damage(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() / 4, player);
                target.setVelocity(target.getVelocity().multiply(2));
            }
        } else {
            // TODO:: add something since the player failed to execute the attack. (originale you would need to use the curse energy within 0.000001 seconds of a physical hit
            //  but since we can't really detect that we just add a random luck based check. ergo: a chance of 1/10000 of hitting.)
        }
    }
}
