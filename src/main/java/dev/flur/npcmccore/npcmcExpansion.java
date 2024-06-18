package dev.flur.npcmccore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return applyHex(value);
        }
        return null;
    }

    private final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]){6}");

    private String applyHex(String msg) {
        Matcher match = hexPattern.matcher(msg);
        while (match.find()) {
            String hex = msg.substring(match.start(), match.end());
            msg = msg.replace(hex, ChatColor.of(hex.substring(1)) + "");
            match = hexPattern.matcher(msg);
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
