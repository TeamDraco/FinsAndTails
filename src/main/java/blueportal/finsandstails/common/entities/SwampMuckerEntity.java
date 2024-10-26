package blueportal.finsandstails.common.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import blueportal.finsandstails.common.entities.ai.goals.WaterJumpGoal;
import blueportal.finsandstails.registry.FTItems;

public class SwampMuckerEntity  extends AbstractSchoolingFish {

    public SwampMuckerEntity(EntityType<? extends SwampMuckerEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new WaterJumpGoal(this, 2));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3).add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    @Override
    public int getMaxSchoolSize() {
        return 5;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.SWAMP_MUCKER_BUCKET.get());
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    @Override
    public SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.SWAMP_MUCKER_SPAWN_EGG.get());
    }
}
