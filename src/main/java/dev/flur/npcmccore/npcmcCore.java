package dev.flur.npcmccore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class npcmcCore extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    Map<String, String> placeholders = new HashMap<>();

    @Override
    public void onEnable() {

        config.addDefault("placeholders.prefix", "&7[&6NPC&7] &r");
        config.options().copyDefaults(true);
        saveConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new npcmcExpansion(this).register();

            if (config.isConfigurationSection("placeholders")) {
                for (String key : config.getConfigurationSection("placeholders").getKeys(false)) {
                    placeholders.put(key, config.getString("placeholders." + key));
                }
            }
        }
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}
