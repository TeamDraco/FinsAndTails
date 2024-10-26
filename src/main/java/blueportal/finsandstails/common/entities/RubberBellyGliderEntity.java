package blueportal.finsandstails.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.common.entities.ai.goals.WaterJumpGoal;
import blueportal.finsandstails.common.entities.ai.control.GroundAndSwimmerNavigator;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;
import blueportal.finsandstails.registry.FTTags;

import java.util.function.Predicate;

public class RubberBellyGliderEntity extends Animal implements GeoEntity {
    private static final EntityDataAccessor<Boolean> PUFFED = SynchedEntityData.defineId(RubberBellyGliderEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions PUFFED_SIZE = EntityDimensions.scalable(0.7f, 0.5f);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private int puffTimer;
    private static final Predicate<Entity> ENEMY_MATCHER = (entity) -> {
        if (entity instanceof Player player) {
            return !player.isCreative() && !player.isSpectator();
        } else {
            return entity instanceof OrnateBugfishEntity;
        }
    };

    public RubberBellyGliderEntity(EntityType<? extends RubberBellyGliderEntity> type, Level world) {
        super(type, world);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 1.0F, true) { // lmao

            @Override
            public void tick() {
                super.tick();
                if (this.mob.isInWater()) {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, -0.0025D, 0.0D));
                }

            }
        };
        this.lookControl = new SmoothSwimmingLookControl(this, 30);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PuffGoal(this));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, OrnateBugfishEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(1, new BreedGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D, 15) {
            @Override
            public boolean canUse() {
                return !this.mob.isInWater() && super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new WaterJumpGoal(this, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, SpindlyGemCrabEntity.class, 90, true, false, null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, RubberBellyGliderEntity::isEntityPrey));
    }

    public static AttributeSupplier.Builder registerRBGAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MAX_HEALTH, 25).add(Attributes.MOVEMENT_SPEED, 0.12);
    }

    private static boolean isEntityPrey(Entity entity) {
        return entity instanceof Squid || entity instanceof BandedRedbackShrimpEntity || entity instanceof WhiteBullCrabEntity|| entity instanceof RedBullCrabEntity;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        setAirSupply(300);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PUFFED, false);
    }

    public boolean isPuffed() {
        return this.entityData.get(PUFFED);
    }

    public void setPuffed(boolean p_203714_1_) {
        this.entityData.set(PUFFED, p_203714_1_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Puffed", this.isPuffed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPuffed(compound.getBoolean("Puffed"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (PUFFED.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return new GroundAndSwimmerNavigator(this, level());
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && !this.isInWater()) {
            float speedMod = getTarget() != null && getTarget().isAlive() ? 2.5F : 1.0F;
            this.setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMod);
        }

        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        }
        else {
            super.travel(travelVector);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 500;
    }

    @Override
    public void tick() {
        super.tick();
        if (isPuffed() && ++puffTimer >= 100) {
            playSound(SoundEvents.PUFFER_FISH_BLOW_OUT, getSoundVolume(), getVoicePitch());
            setPuffed(false);
            puffTimer = 0;
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.RUBBER_BELLY_GLIDER_SPAWN_EGG.get());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
        return FTEntities.RUBBER_BELLY_GLIDER.get().create(level());
    }

    @Override
    public void playerTouch(Player entityIn) {
        if (entityIn instanceof ServerPlayer && isPuffed()) {
            entityIn.hurt(this.level().damageSources().mobAttack(this), 2);
            entityIn.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FTSounds.RUBBER_BELLY_GLIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FTSounds.RUBBER_BELLY_GLIDER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FTSounds.RUBBER_BELLY_GLIDER_HURT.get();
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return isBaby() ? 0.05f : 0.15f;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return isPuffed() ? PUFFED_SIZE : super.getDimensions(poseIn);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(FTTags.SPINDLY_GEM_CRABS);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoEntity>(this, "controller", 5, this::predicate));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving() && isInWater()) {
            if (isPuffed()) {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.swim_puffed"));
            }
            else {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.swim"));
            }
        }
        else if (!event.isMoving() && isInWater()) {
            if (isPuffed()) {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.idle_puffed"));
            }
            else {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.idle"));
            }
        }
        else if (!isInWater()) {
            boolean walking = !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F);
            if (walking) {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.walk"));
                event.getController().setAnimationSpeed(1.45F);
            }
            else {
                event.setAnimation(RawAnimation.begin().thenLoop("animation.rubber_belly_glider.idle_land"));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    static class PuffGoal extends Goal {
        private final RubberBellyGliderEntity glider;

        public PuffGoal(RubberBellyGliderEntity glider) {
            this.glider = glider;
        }

        @Override
        public boolean canUse() {
            return glider.isInWater() && !glider.isPuffed() && !glider.level().getEntities(glider, this.glider.getBoundingBox().inflate(2.5D), RubberBellyGliderEntity.ENEMY_MATCHER).isEmpty();
        }

        @Override
        public void start() {
            glider.playSound(SoundEvents.PUFFER_FISH_BLOW_UP, glider.getSoundVolume(), glider.getVoicePitch());
            glider.setPuffed(true);
        }
    }
}