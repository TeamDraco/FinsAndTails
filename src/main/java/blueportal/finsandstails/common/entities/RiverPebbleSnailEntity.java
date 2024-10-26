package blueportal.finsandstails.common.entities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;

public class RiverPebbleSnailEntity extends Animal implements GeoEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(RiverPebbleSnailEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SHIMMER = SynchedEntityData.defineId(RiverPebbleSnailEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public int shimmerTime = 20;
    public int shimmerCooldown = this.random.nextInt(100) + 200;

    public RiverPebbleSnailEntity(EntityType<? extends RiverPebbleSnailEntity> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    public float getStepHeight() {
        return 1.0F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void registerGoals() {
        this.goalSelector.addGoal(0, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, GoldenRiverRayEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(Items.BROWN_MUSHROOM), false));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(FTItems.PEBBLE_SHELL.get(), 1);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4).add(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && getVariant() == 5) {
            if (--this.shimmerCooldown <= 0) {
                this.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.shimmerCooldown = this.random.nextInt(100) + 200;
                setShimmer(true);
                this.shimmerTime = 20;
            }
            if (--this.shimmerTime <= 0) {
                setShimmer(false);
                this.shimmerTime = 0;
            }
        }
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENDERMITE_STEP, 0.15F, 1.0F);
    }

    public float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
        RiverPebbleSnailEntity snail = FTEntities.RIVER_PEBBLE_SNAIL.get().create(world);
        snail.setVariant(random.nextInt(5));

        return snail;
    }

    public float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.25F;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.RIVER_PEBBLE_SNAIL_SPAWN_EGG.get());
    }

    public static boolean canSnailSpawn(EntityType<? extends Animal> entity, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return level.getFluidState(pos).is(FluidTags.WATER) /*&& level.getBlockState(pos.above()).is(Blocks.WATER)*/;
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.BROWN_MUSHROOM;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() == Items.FLOWER_POT && this.isAlive() && !this.isBaby()) {
            playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(FTItems.RIVER_PEBBLE_SNAIL_POT.get());
            this.setBucketData(itemstack1);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
                heldItem.getOrCreateTag().putInt("Age", getAge());
            }
            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private void setBucketData(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag == null) {
            if (random.nextFloat() > 0.95F) {
                setVariant(5);
            }
            else {
                setVariant(random.nextInt(5));
            }
        } else {
            if (dataTag.contains("Variant", 3)) {
                this.setVariant(dataTag.getInt("Variant"));
            }
        }
        return spawnDataIn;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(IS_SHIMMER, false);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean getShimmer() {
        return this.entityData.get(IS_SHIMMER);
    }

    private void setShimmer(boolean shimmer) {
        this.entityData.set(IS_SHIMMER, shimmer);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
    }

    public PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "controller", 5, this::predicate));
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "shimmer_controller", 5, this::shimmerPredicate));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.setAnimation(RawAnimation.begin().thenLoop("animation.snail.walk"));
        }
        else {
            event.setAnimation(RawAnimation.begin().thenLoop("animation.snail.idle"));
        }
        return PlayState.CONTINUE;
    }

    private <E extends GeoEntity> PlayState shimmerPredicate(AnimationState<E> event) {
        if (getVariant() == 5 && getShimmer()) {
            event.setAnimation(RawAnimation.begin().thenPlay("animation.snail.shimmer"));
            return PlayState.CONTINUE;
        }
        else {
            return PlayState.STOP;
        }
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.00005F;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    static class MoveHelperController extends MoveControl {
        private final RiverPebbleSnailEntity snail;

        MoveHelperController(RiverPebbleSnailEntity snail) {
            super(snail);
            this.snail = snail;
        }

        public void tick() {
            if (this.snail.horizontalCollision && this.snail.level().getBlockState(this.snail.blockPosition().above()).getBlock() == Blocks.WATER) {
                this.snail.setDeltaMovement(this.snail.getDeltaMovement().add(0.0D, 0.025D, 0.0D));
            }
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.snail.getNavigation().isDone()) {
                double d0 = this.wantedX - this.snail.getX();
                double d1 = this.wantedY - this.snail.getY();
                double d2 = this.wantedZ - this.snail.getZ();
                double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
                d1 = d1 / d3;
                float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.snail.setYRot(this.rotlerp(this.snail.getYRot(), f, 90.0F));
                this.snail.yBodyRot = this.snail.getYRot();

                float f1 = (float) (this.speedModifier * this.snail.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (snail.isInWater()) {
                    float speedMod = 5.0F;
                    f1 = f1 * speedMod;
                }

                this.snail.setSpeed(Mth.lerp(0.125F, this.snail.getSpeed(), f1));
                this.snail.setDeltaMovement(this.snail.getDeltaMovement().add(0.0D, (double) this.snail.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.snail.setSpeed(0.0F);
            }
        }
    }
}