package betterchairs.nms.v1_8_R1;

import de.sprax2013.betterchairs.ChairUtils;
import de.sprax2013.betterchairs.CustomChairEntity;
import net.minecraft.server.v1_8_R1.EntityArrow;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;

class CustomArrow extends EntityArrow implements CustomChairEntity {
    private boolean remove = false;
    private final int regenerationAmplifier;

    /**
     * @param regenerationAmplifier provide a negative value to disable regeneration
     */
    public CustomArrow(World world, double d0, double d1, double d2, int regenerationAmplifier) {
        super(world, d0, d1, d2);

        this.regenerationAmplifier = regenerationAmplifier;
    }

    @Override
    public void markAsRemoved() {
        this.remove = true;
    }

    @Override
    public void s_() {
        if (this.remove) return; // If the entity is being removed, no need to bother
        if (this.ticksLived % 10 == 0) return; // Only run every 10 ticks

        if (!(this.passenger instanceof EntityHuman)) {
            this.remove = true;
            this.bukkitEntity.remove();
            return;
        }

        // Rotate the entity together with its passenger
        this.setYawPitch(this.passenger.yaw, 0);

        ChairUtils.applyRegeneration(((EntityHuman) this.passenger).getBukkitEntity(), this.regenerationAmplifier);
    }

    @Override
    public void die() {
        // Prevents the ArmorStand from getting killed unexpectedly
        if (shouldDie()) super.die();
    }

    private boolean shouldDie() {
        return this.remove || this.passenger == null || !(this.passenger instanceof EntityHuman);
    }
}
