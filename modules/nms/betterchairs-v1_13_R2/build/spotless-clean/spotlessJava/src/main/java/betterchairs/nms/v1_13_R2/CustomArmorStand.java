package betterchairs.nms.v1_13_R2;

import de.sprax2013.betterchairs.ChairManager;
import de.sprax2013.betterchairs.ChairUtils;
import de.sprax2013.betterchairs.CustomChairEntity;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityArmorStand;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;

class CustomArmorStand extends EntityArmorStand implements CustomChairEntity {
    private boolean remove = false;
    private final int regenerationAmplifier;

    private final Location expectedLocation;

    /**
     * @param regenerationAmplifier provide a negative value to disable regeneration
     */
    public CustomArmorStand(World world, double d0, double d1, double d2, int regenerationAmplifier) {
        super(world, d0, d1, d2);

        this.regenerationAmplifier = regenerationAmplifier;
        this.expectedLocation = new Location(null, d0, d1, d2);
    }

    @Override
    public void markAsRemoved() {
        this.remove = true;
    }

    @Override
    public void tick() {
        if (this.remove) return; // If the ArmorStand is being removed, no need to bother
        if (this.ticksLived % 10 == 0) return; // Only run every 10 ticks

        Entity passenger = this.passengers.isEmpty() ? null : this.passengers.get(0);

        if (!(passenger instanceof EntityHuman)) {
            this.remove = true;
            this.getBukkitEntity().remove();
            return;
        }

        // Rotate the ArmorStand together with its passenger
        // Not happy about using Bukkit API here (+ scheduling) but I don't see a good alternative with all the
        // obfuscation
        Bukkit.getScheduler().runTask(ChairManager.getPlugin(), () -> this.getBukkitEntity()
                .setRotation(passenger.getBukkitYaw(), 0));

        if (ChairUtils.didChairEntityMove(this.expectedLocation, this.locX, this.locY, this.locZ)) {
            this.enderTeleportTo(
                    this.expectedLocation.getX(),
                    Math.min(this.locY, this.expectedLocation.getY()),
                    this.expectedLocation.getZ());
        }

        ChairUtils.applyRegeneration(((EntityHuman) passenger).getBukkitEntity(), this.regenerationAmplifier);
    }

    @Override
    public void killEntity() {
        // Prevents the ArmorStand from getting killed unexpectedly
        if (shouldDie()) super.killEntity();
    }

    @Override
    public void die() {
        // Prevents the ArmorStand from getting killed unexpectedly
        if (shouldDie()) super.die();
    }

    private boolean shouldDie() {
        return this.remove || this.passengers.isEmpty() || !(this.passengers.get(0) instanceof EntityHuman);
    }
}
