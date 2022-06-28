package de.presti.animestuff;

import org.bukkit.plugin.java.JavaPlugin;

public final class AnimeStuff extends JavaPlugin {

    private static AnimeStuff instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = new AnimeStuff();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AnimeStuff getInstance() {
        return instance;
    }
}
