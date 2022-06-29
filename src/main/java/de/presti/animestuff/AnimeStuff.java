package de.presti.animestuff;

import de.presti.animestuff.command.TestCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnimeStuff extends JavaPlugin {

    private static AnimeStuff instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        startUp();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void startUp() {
        saveDefaultConfig();
        reloadConfig();

        //sendPluginMessage();
        //registerListeners();
        registerCommands();
    }

    private void registerCommands() {
        instance.getCommand("domainexpansion").setExecutor(new TestCMD());
    }

    public static AnimeStuff getInstance() {
        return instance;
    }
}
