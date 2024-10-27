package blueportal.finsandstails.mixin.client;

import blueportal.finsandstails.common.entities.PenglilEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(
            method = {"Lnet/minecraft/world/entity/Entity;positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V"},
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void FT$positionRider(Entity entity, Entity.MoveFunction p_19958_, CallbackInfo ci) {
        if (entity instanceof PenglilEntity penglil && entity.getVehicle() instanceof Player player) {
            double offset = entity.getPassengersRidingOffset() + entity.getMyRidingOffset();
            double crouchingOffset = player.isCrouching() ? -0.3F : 0.0F;
            Vec3 pos = getYawVec(player.yBodyRot, 0.35F, 0.0F).add(player.position()).add(0.0F, offset + crouchingOffset, 0.0F);
            p_19958_.accept(penglil, pos.x, pos.y, pos.z);
            ci.cancel();
        }
    }

    private static Vec3 getYawVec(float yaw, double xOffset, double zOffset) {
        return new Vec3(xOffset, 0, zOffset).yRot(-yaw * ((float) Math.PI / 180f));
    }
}
