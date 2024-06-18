package dev.flur.npcmccore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class npcmcExpansion extends PlaceholderExpansion {
    private final npcmcCore plugin;

    public npcmcExpansion(npcmcCore plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors()); //
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "npcmc";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion(); //
    }

    @Override
    public boolean persist() {
        return true; //
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String value = plugin.getPlaceholders().get(params);
        if (value != null) {
            return ChatColor.translateAlternateColorCodes('&', value);
        }

        return null;
    }
}
