package blueportal.finsandstails.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.common.entities.ai.goals.SpearMeleeAttackGoal;
import blueportal.finsandstails.common.entities.ai.control.FTSmoothSwimmingMoveControl;
import blueportal.finsandstails.registry.FTItems;

public class TealArrowfishEntity extends AbstractSchoolingFish implements GeoEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public int killCooldown = 0;

    public TealArrowfishEntity(EntityType<? extends TealArrowfishEntity> type, Level world) {
        super(type, world);
        this.moveControl = new FTSmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SpearMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WeeEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WeeWeeEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VibraWeeEntity.class, false));
    }

    @Override
    public boolean killedEntity(ServerLevel p_216988_, LivingEntity p_216989_) {
        this.killCooldown = this.random.nextInt(600) + 1200;
        return super.killedEntity(p_216988_, p_216989_);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("KillCooldownTime")) {
            this.killCooldown = tag.getInt("KillCooldownTime");
        }
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("KillCooldownTime", this.killCooldown);
    }

    @Override
    public int getMaxSchoolSize() {
        return 3;
    }

    @Override
    protected float getStandingEyeHeight(Pose p_27474_, EntityDimensions p_27475_) {
        return p_27475_.height * 0.25F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3).add(Attributes.ATTACK_DAMAGE, 0.5);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.TEAL_ARROWFISH_BUCKET.get());
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    public SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.TEAL_ARROWFISH_SPAWN_EGG.get());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "controller", 5, this::predicate));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.setAnimation(RawAnimation.begin().thenPlay("animation.arrowfish.swim"));
        }
        else {
            event.setAnimation(RawAnimation.begin().thenPlay("animation.arrowfish.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
