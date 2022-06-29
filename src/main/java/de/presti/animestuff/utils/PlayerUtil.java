package de.presti.animestuff.utils;

import de.presti.animestuff.AnimeStuff;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nonnull;
import java.util.List;

public class PlayerUtil {

    public static void occupyPlayer(@Nonnull Player player) {
        player.setMetadata("AS_OCCUPIED", new FixedMetadataValue(AnimeStuff.getInstance(), true));
    }

    public static void freePlayer(@Nonnull Player player) {
        player.removeMetadata("AS_OCCUPIED", AnimeStuff.getInstance());
    }

    public static boolean isOccupied(@Nonnull Player player) {
        if (!player.hasMetadata("AS_OCCUPIED")) {
            return false;
        }
        List<MetadataValue> metaDataList = player.getMetadata("AS_OCCUPIED");

        return metaDataList.size() == 1 && metaDataList.get(0).asBoolean();
    }

}
