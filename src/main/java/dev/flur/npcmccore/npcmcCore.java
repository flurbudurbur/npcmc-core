package dev.flur.npcmccore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    // register a command
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (label.equalsIgnoreCase("reload")) {
                reloadConfig();
                p.sendMessage(ChatColor.GREEN + "NPCMC reloaded!");
                return true;
            }
        }
        return false;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}
