package blueportal.finsandstails.common.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.Tags;
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

public class SpindlyGemCrabEntity extends AbstractFish implements GeoEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SpindlyGemCrabEntity.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public SpindlyGemCrabEntity(EntityType<? extends SpindlyGemCrabEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new TemptGoal(this, 1.0D, Ingredient.of(Tags.Items.GEMS), false));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.SPINDLY_GEM_CRAB_BUCKET.get());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag == null) {
            setVariant(random.nextInt(5));
        } else {
            if (dataTag.contains("Variant", 3)){
                this.setVariant(dataTag.getInt("Variant"));
            }
        }
        return spawnDataIn;
    }

    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant()); 
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
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

    public void handleEntityEvent(byte id) {
        if (id == 38) {
            this.shineParticles(ParticleTypes.END_ROD);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    private void shineParticles(SimpleParticleType p_208401_1_) {
        if (random.nextFloat() > 0.975D) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(p_208401_1_, this.getX(), this.getRandomY(), this.getZ(), d0, d1, d2);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D);
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
        return new ItemStack(FTItems.SPINDLY_GEM_CRAB_SPAWN_EGG.get());
    }

    @Override
    public void tick() {
        super.tick();
        level().broadcastEntityEvent(this, (byte)38);
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
        boolean walking = !(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F);
        if (walking){
            event.setAnimation(RawAnimation.begin().thenLoop("animation.Spindly_crab.move"));
        } else {
            event.setAnimation(RawAnimation.begin().thenLoop("animation.spindly_crab.idle"));
        }
        return PlayState.CONTINUE;
    }

    static class MoveHelperController extends MoveControl {
        private final SpindlyGemCrabEntity crab;

        MoveHelperController(SpindlyGemCrabEntity crab) {
            super(crab);
            this.crab = crab;
        }

        public void tick() {
            if (this.crab.isEyeInFluid(FluidTags.WATER)) {
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, 0.0D, 0.0D));
            }

            if (this.crab.horizontalCollision && this.crab.level().getBlockState(this.crab.blockPosition().above()).getBlock() == Blocks.WATER) {
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, 0.025D, 0.0D));
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