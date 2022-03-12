package teamdraco.finsandstails.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import teamdraco.finsandstails.common.entities.ai.MudhorseForageGoal;
import teamdraco.finsandstails.registry.FTEntities;
import teamdraco.finsandstails.registry.FTItems;
import teamdraco.finsandstails.registry.FTSounds;

import java.util.EnumSet;

public class MudhorseEntity extends Animal implements IAnimatable, IAnimationTickable {
    public static final EntityDataAccessor<Boolean> FORAGING = SynchedEntityData.defineId(MudhorseEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private LivingEntity commander;
    private int commanderSetTime;
    private int attackTimer;
    private MudhorseForageGoal eatBlockGoal;

    public MudhorseEntity(EntityType<? extends MudhorseEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatBlockGoal = new MudhorseForageGoal(this);
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(FTItems.SWAMP_MUCKER.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new CommanderHurt(this));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30).add(Attributes.ATTACK_DAMAGE, 2).add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    public boolean isForaging() {
        return this.entityData.get(FORAGING);
    }

    public void setForaging(boolean p_213419_1_) {
        this.entityData.set(FORAGING, p_213419_1_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FORAGING, false);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();

            if (entity != null && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 10;
        this.level.broadcastEntityEvent(this, (byte) 4);
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (flag) {
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, (double) 0.3F, 0.0D));
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

        @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == FTItems.SWAMP_MUCKER.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FTSounds.MUDHORSE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FTSounds.MUDHORSE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FTSounds.MUDHORSE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.HORSE_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void aiStep() {
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (commanderSetTime > 0) {
            --commanderSetTime;
        } else {
            commander = null;
        }

        super.aiStep();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
        return FTEntities.MUDHORSE.get().create(world);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.7F : 1.4F;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.MUDHORSE_SPAWN_EGG.get());
    }

    public LivingEntity getCommander() {
        return commander;
    }

    public void setCommander(LivingEntity commander) {
        this.commander = commander;
        commanderSetTime = 600;
    }

    @Override
    public boolean canAttack(LivingEntity p_21171_) {
        return !(p_21171_ instanceof MudhorseEntity);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mudhorse.walk", true));
        }
        else if (isForaging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mudhorse.grazing", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mudhorse.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    private static class CommanderHurt extends TargetGoal {
        private final MudhorseEntity mudhorse;
        private LivingEntity attacker;
        private int timestamp;

        public CommanderHurt(MudhorseEntity mudhorse) {
            super(mudhorse, false);
            this.mudhorse = mudhorse;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.mudhorse.getCommander();
            if (livingentity != null) {
                this.attacker = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                if (i != this.timestamp && this.canAttack(this.attacker, TargetingConditions.DEFAULT)) {
                    if (!(targetMob instanceof Creeper) && !(targetMob instanceof Ghast)) {
                        if (targetMob instanceof Wolf) {
                            Wolf wolfentity = (Wolf) targetMob;
                            return !wolfentity.isTame() || wolfentity.getOwner() != livingentity;
                        } else if (targetMob instanceof Player && livingentity instanceof Player && !((Player) livingentity).canHarmPlayer((Player) targetMob)) {
                            return false;
                        } else if (targetMob instanceof AbstractHorse && ((AbstractHorse) targetMob).isTamed()) {
                            return false;
                        } else {
                            return !(targetMob instanceof TamableAnimal) || !((TamableAnimal) targetMob).isTame();
                        }
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingentity = this.mudhorse.getCommander();
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtMobTimestamp();
            }

            super.start();
        }
    }
}
