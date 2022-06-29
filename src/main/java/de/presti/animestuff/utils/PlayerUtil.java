package de.presti.animestuff.utils;

import de.presti.animestuff.AnimeStuff;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nonnull;
import java.util.List;

public class PlayerUtil {

    public static void occupyPlayer(@Nonnull Player player, @Nonnull Player occupiedBy) {
        player.setMetadata("AS_OCCUPIED", new FixedMetadataValue(AnimeStuff.getInstance(), occupiedBy));
    }

    public static void freePlayer(@Nonnull Player player) {
        player.removeMetadata("AS_OCCUPIED", AnimeStuff.getInstance());
    }

    public static Player getOccupiedBy(@Nonnull Player player) {
        if (!isOccupied(player)) {
            return null;
        }

        List<MetadataValue> metadata = player.getMetadata("AS_OCCUPIED");

        if (metadata.isEmpty()) {
            return null;
        }

        return (Player) metadata.get(0).value();
    }

    public static boolean isOccupied(@Nonnull Player player) {
        return player.hasMetadata("AS_OCCUPIED");
    }

}
