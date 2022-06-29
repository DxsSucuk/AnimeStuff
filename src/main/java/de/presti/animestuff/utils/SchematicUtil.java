package de.presti.animestuff.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
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
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.math.transform.CombinedTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import de.presti.animestuff.AnimeStuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class SchematicUtil {

    public static void loadAndPasteSchematic(@Nonnull String path, @Nonnull Player player) {
        loadAndPasteSchematic(path, player, player.getLocation());
    }
    public static void loadAndPasteSchematic(@Nonnull String path, @Nonnull Player player, @Nonnull Location location) {
        pasteSchematic(loadSchematic(path), player, location);
    }

    private static PasteBuilder getPasteBuilder(Clipboard clipboard, EditSession editSession, Location location, int rotationY, int rotationX, int rotationZ) {
        ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);

        clipboardHolder.setTransform(clipboardHolder.getTransform().combine(new AffineTransform()
                .rotateY(rotationY)
                .rotateX(rotationX)
                .rotateZ(rotationZ)));

        return clipboardHolder
                .createPaste(editSession)
                .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                .ignoreAirBlocks(false);
    }

    public static void pasteSchematic(@Nonnull Clipboard clipboard, @Nonnull Player player) {
        Location location = player.getLocation();
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") instanceof WorldEditPlugin worldEditPlugin) {
            try (EditSession editSession = worldEditPlugin.getWorldEdit().newEditSession(BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName()))))) {

                Operation operation = getPasteBuilder(clipboard, editSession, location, 0, 0, 0).build();
                Operations.complete(operation);

                worldEditPlugin.getSession(player).remember(editSession);
            } catch (Exception exception) {
                AnimeStuff.getInstance().getLogger().warning("Failure loading a schematic.");
                exception.printStackTrace();
            }
        }
    }

    public static void pasteSchematic(Clipboard clipboard, Player player, Location location) {
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") instanceof WorldEditPlugin worldEditPlugin) {
            try (EditSession editSession = worldEditPlugin.getWorldEdit().newEditSession(BukkitAdapter.adapt(player.getWorld()))) {

                Operation operation = getPasteBuilder(clipboard, editSession, location, 0, 0, 0).build();
                Operations.complete(operation);

                worldEditPlugin.getSession(player).remember(editSession);
            } catch (Exception exception) {
                AnimeStuff.getInstance().getLogger().warning("Failure loading a schematic.");
                exception.printStackTrace();
            }
        }
    }

    public static Clipboard loadSchematic(String path) {
        File schematic = new File("plugins/AnimeStuff/", path);

        if (!schematic.exists() || !schematic.isFile()) {
            AnimeStuff.getInstance().getLogger().warning("Schematic file not found: " + path);
            return null;
        }

        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);

        if (format == null) {
            AnimeStuff.getInstance().getLogger().warning("Schematic file format not supported: " + path);
            return null;
        }

        try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (clipboard == null) {
            AnimeStuff.getInstance().getLogger().warning("Could not load schematic: " + path);
            return null;
        }

        return clipboard;
    }

    public static void revertLastSchematic(Player player) {
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") instanceof WorldEditPlugin worldEditPlugin) {
            LocalSession localSession = worldEditPlugin.getSession(player);
            try (EditSession editSession = localSession.undo(null, BukkitAdapter.adapt(player))) {
                Operation operation = editSession.commit();
                Operations.complete(operation);
            } catch (Exception exception) {
                AnimeStuff.getInstance().getLogger().warning("Failure reverting a schematic.");
                exception.printStackTrace();
            }
        }
    }
}
