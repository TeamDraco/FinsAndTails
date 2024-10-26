package blueportal.finsandstails.common.entities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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

public class RedBullCrabEntity extends WaterAnimal implements GeoEntity {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(RedBullCrabEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public RedBullCrabEntity(EntityType<? extends RedBullCrabEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, RedBullCrabEntity.class, false));
    }

    @Override
    public float getWaterSlowDown() {
        return 0.9f;
    }

    public PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    public ItemStack getFishBucket() {
        return new ItemStack(FTItems.RED_BULL_CRAB_BUCKET.get());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15).add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth());
    }

    public boolean requiresCustomPersistence() {
        return !this.isFromBucket();
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
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
        compound.put("Attributes", this.getAttributes().save());
        compound.putFloat("Health", this.getHealth());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        if (compound.contains("Attributes", 9) && this.level() != null && !this.level().isClientSide) {
            this.getAttributes().load(compound.getList("Attributes", 10));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag dataTag) {
        this.randomizeAttributes();

        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, dataTag);
    }

    public float generateRandomMaxHealth() {
        return 6.0F + (float)this.random.nextInt(9);
    }

    @Nullable
    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    public SoundEvent getDeathSound() {
        return FTSounds.CRAB_DEATH.get();
    }

    public SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.RED_BULL_CRAB_SPAWN_EGG.get());
    }

    @Override
    public void handleAirSupply(int p_209207_1_) {
    }

    public static boolean canCrabSpawn(EntityType<? extends WaterAnimal> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos p_223363_3_, RandomSource randomIn) {
        return worldIn.getBlockState(p_223363_3_).is(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.above()).is(Blocks.WATER);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
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

    public void setBucketData(ItemStack stack) {
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.setAnimation(RawAnimation.begin().thenLoop("animation.bullcrab.walk"));
        }
        else {
            event.setAnimation(RawAnimation.begin().thenLoop("animation.bullcrab.idle"));
        }
        return PlayState.CONTINUE;
    }

    static class MoveHelperController extends MoveControl {
        private final Mob crab;

        MoveHelperController(Mob crab) {
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