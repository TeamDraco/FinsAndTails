package blueportal.finsandstails.common.entities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import javax.annotation.Nullable;
import java.util.List;

public class WhiteBullCrabEntity extends WaterAnimal implements GeoEntity {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(WhiteBullCrabEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public WhiteBullCrabEntity(EntityType<? extends WhiteBullCrabEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, RedBullCrabEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    protected PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9f;
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(FTItems.WHITE_BULL_CRAB_BUCKET.get());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4).add(Attributes.MOVEMENT_SPEED, 0.15D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void tick() {
        super.tick();
        List<WhiteBullCrabEntity> list = level().getEntitiesOfClass(WhiteBullCrabEntity.class, getBoundingBox().inflate(20D));

        if (list.size() >= 8) {
            for (WhiteBullCrabEntity crab : list) {
                crab.setTarget(list.get(random.nextInt(list.size())));
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FTSounds.CRAB_DEATH.get();
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.WHITE_BULL_CRAB_SPAWN_EGG.get());
    }

    @Override
    protected void handleAirSupply(int p_209207_1_) {
    }

    public boolean requiresCustomPersistence() {
        if (this.isFromBucket()) {
            return false;
        }
        else {
            return true;
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    protected InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            this.setBucketData(itemstack1);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)p_230254_1_, itemstack1);
            }

            if (itemstack.isEmpty()) {
                p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
            } else if (!p_230254_1_.getInventory().add(itemstack1)) {
                p_230254_1_.drop(itemstack1, false);
            }

            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "controller", 5, this::predicate));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.bullcrab.walk"));
        }
        else {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.bullcrab.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    static class MoveHelperController extends MoveControl {
        private final WhiteBullCrabEntity crab;

        MoveHelperController(WhiteBullCrabEntity crab) {
            super(crab);
            this.crab = crab;
        }

        public void tick() {
            if (this.crab.isEyeInFluid(FluidTags.WATER)) {
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, 0.0D, 0.0D));
            }

            if (this.operation == Operation.MOVE_TO && !this.crab.getNavigation().isDone()) {
                double d0 = this.wantedX - this.crab.getX();
                double d1 = this.wantedY - this.crab.getY();
                double d2 = this.wantedZ - this.crab.getZ();
                double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
                d1 = d1 / d3;
                float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.crab.setYRot(this.rotlerp(this.crab.getYRot(), f, 90.0F));
                this.crab.yBodyRot = this.crab.getYRot();
                float f1 = (float) (this.speedModifier * this.crab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.crab.setSpeed(Mth.lerp(0.125F, this.crab.getSpeed(), f1));
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, (double) this.crab.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.crab.setSpeed(0.0F);
            }
        }
    }
}