package de.sprax2013.betterchairs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Settings {
    public static final String SIT_ON_ARROWS = "Chairs.SitOnArrows";
    public static final String ALLOWED_DISTANCE_TO_CHAIR = "Chairs.AllowedDistanceToChair";
    public static final String AUTO_ROTATE_PLAYER = "Chairs.AutoRotatePlayer";
    public static final String NEEDS_EMPTY_HANDS = "Chairs.NeedEmptyHands";
    public static final String NEEDS_SIGNS = "Chairs.NeedsSignsOnBothSides";
    public static final String IGNORES_INTERACT_PREVENTION = "Chairs.IgnoreOtherPluginsPreventingInteract";
    public static final String HAVE_CHAIRS_DISABLED_FOR_PLAYER_BY_DEFAULT =
            "Chairs.HaveChairsDisabledForPlayerByDefault";
    public static final String REMEMBER_IF_PLAYER_DISABLED_CHAIRS =
            "Chairs.RememberIfPlayerDisabledChairsAfterRelogin";
    public static final String CHAIR_NEED_AIR_ABOVE = "Chairs.Position.NeedAirAbove";
    public static final String CHAIR_ALLOW_AIR_BELOW = "Chairs.Position.AllowAirBelow";
    public static final String USE_STAIRS = "Chairs.UseStairs";
    public static final String USE_SLABS = "Chairs.UseSlabs";
    public static final String LEAVING_CHAIR_TELEPORT_TO_OLD_LOCATION =
            "Chairs.LeavingChair.TeleportPlayerToOldLocation";
    public static final String LEAVING_CHAIR_KEEP_HEAD_ROTATION = "Chairs.LeavingChair.KeepHeadRotation";
    public static final String ALLOW_SWITCHING_SEATS = "Chairs.AllowSwitchingSeats";
    public static final String MSG_ALREADY_OCCUPIED = "Chairs.Messages.AlreadyOccupied";
    public static final String MSG_NEEDS_SIGNS = "Chairs.Messages.NeedsSignsOnBothSides";
    public static final String MSG_NOW_SITTING = "Chairs.Messages.NowSitting";
    public static final String REGENERATION_ENABLED = "Chairs.Regeneration.Enabled";
    public static final String REGENERATION_CHECK_PERMISSION = "Chairs.Regeneration.CheckPermission";
    public static final String REGENERATION_AMPLIFIER = "Chairs.Regeneration.Amplifier";
    public static final String WORLD_FILTER_ENABLED = "Filter.Worlds.Enabled";
    public static final String WORLD_FILTER_AS_BLACKLIST = "Filter.Worlds.UseAsBlacklist";
    public static final String WORLD_FILTER_NAMES = "Filter.Worlds.Names";
    public static final String MATERIAL_FILTER_ENABLED = "Filter.Blocks.Enabled";
    public static final String MATERIAL_FILTER_ALLOW_ALL_TYPES = "Filter.Blocks.AllowAllTypes";
    public static final String MATERIAL_FILTER_AS_BLACKLIST = "Filter.Blocks.UseAsBlacklist";
    public static final String MATERIAL_FILTER_NAMES = "Filter.Blocks.Names";

    private static final List<Runnable> listeners = new ArrayList<>();

    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    private static FileConfiguration cfg() {
        JavaPlugin plugin = ChairManager.getPlugin();
        return plugin == null ? null : plugin.getConfig();
    }

    public static boolean bool(String key) {
        FileConfiguration c = cfg();
        return c != null && c.getBoolean(key);
    }

    public static int integer(String key) {
        FileConfiguration c = cfg();
        return c == null ? 0 : c.getInt(key);
    }

    public static List<String> stringList(String key) {
        FileConfiguration c = cfg();
        return c == null ? Collections.emptyList() : c.getStringList(key);
    }

    public static boolean reload() {
        JavaPlugin plugin = ChairManager.getPlugin();
        if (plugin == null) {
            return false;
        }
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        for (Runnable r : listeners.toArray(new Runnable[0])) {
            r.run();
        }
        return true;
    }

    public static void addListener(Runnable r) {
        listeners.add(Objects.requireNonNull(r));
    }

    public static void reset() {
        listeners.clear();
    }
}
