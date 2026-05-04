package de.sprax2013.betterchairs;

import java.io.File;
import java.util.Objects;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Messages {
    public static final String ERR_ASYNC_API_CALL = "Async API call";
    public static final String ERR_ANOTHER_PLUGIN_PREVENTING_SPAWN =
            "Looks like another plugin is preventing BetterChairs from spawning chairs";
    public static final String ERR_NOT_CUSTOM_ENTITY = "The provided Entity is not an instance of '%s'";

    public static final String PREFIX = "General.Prefix";
    public static final String NO_PERMISSION = "General.NoPermission";

    public static final String TOGGLE_ENABLED = "ToggleChairs.Enabled";
    public static final String TOGGLE_DISABLED = "ToggleChairs.Disabled";
    public static final String TOGGLE_STATUS_ENABLED = "ToggleChairs.Status.Enabled";
    public static final String TOGGLE_STATUS_DISABLED = "ToggleChairs.Status.Disabled";

    public static final String USE_ALREADY_OCCUPIED = "ChairUse.AlreadyOccupied";
    public static final String USE_NEEDS_SIGNS = "ChairUse.NeedsSignsOnBothSides";
    public static final String USE_NOW_SITTING = "ChairUse.NowSitting";

    private static YamlConfiguration yaml;

    private Messages() {
        throw new IllegalStateException("Utility class");
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(yaml.getString(PREFIX)));
    }

    public static String getString(String key) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(yaml.getString(key)))
                .replace("${Prefix}", getPrefix());
    }

    public static boolean reload() {
        JavaPlugin plugin = ChairManager.getPlugin();
        if (plugin == null) {
            return false;
        }
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        yaml = YamlConfiguration.loadConfiguration(file);
        return true;
    }

    public static void reset() {
        yaml = null;
    }
}
