package de.sprax2013.betterchairs;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EssentialsSupport {
    private EssentialsSupport() {
        throw new IllegalStateException("Utility class");
    }

    static boolean teleportPreservingBack(@NotNull Player player, @NotNull Location location) {
        User user = getEssentialsUser(player);
        Location lastLocation = user != null ? user.getLastLocation() : null;
        boolean teleported = player.teleport(location);

        if (teleported && user != null && lastLocation != null) {
            user.setLastLocation(lastLocation);
        }

        return teleported;
    }

    @Nullable
    private static User getEssentialsUser(@NotNull Player player) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");

        if (!(plugin instanceof IEssentials)) {
            return null;
        }

        return ((IEssentials) plugin).getUser(player);
    }
}
