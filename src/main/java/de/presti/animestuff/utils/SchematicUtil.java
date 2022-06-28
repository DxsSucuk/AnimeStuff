package de.presti.animestuff.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.MCEditSchematicReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.CombinedTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import de.presti.animestuff.AnimeStuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SchematicUtil {

    public static void loadSchematic(String path, Player player, int rotation) {
        File schematic = new File(path);

        if (!schematic.exists() || !schematic.isFile()) {
            AnimeStuff.getInstance().getLogger().warning("Schematic file not found: " + path);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("WorldEdit") instanceof WorldEditPlugin worldEditPlugin) {
            try (EditSession editSession = worldEditPlugin.createEditSession(player)) {
                Clipboard clipboard;

                ClipboardFormat format = ClipboardFormats.findByFile(schematic);

                if (format == null) {
                    AnimeStuff.getInstance().getLogger().warning("Schematic file format not supported: " + path);
                    return;
                }

                try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
                    clipboard = reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (clipboard == null) {
                    AnimeStuff.getInstance().getLogger().warning("Could not load schematic: " + path);
                    return;
                }

                ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);

                PasteBuilder pasteBuilder = clipboardHolder
                        .createPaste(editSession)
                        .to(BlockVector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));

                Operation operation = pasteBuilder.build();

                Operations.complete(operation);

                worldEditPlugin.getSession(player).remember(editSession);
            } catch (Exception exception) {
                AnimeStuff.getInstance().getLogger().warning("Could not load schematic: " + path);
                exception.printStackTrace();
            }
        }
    }

}
