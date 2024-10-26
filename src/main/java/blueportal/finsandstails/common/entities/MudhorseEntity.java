package blueportal.finsandstails.common.entities;

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
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import blueportal.finsandstails.common.entities.ai.goals.MudhorseForageGoal;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import java.util.EnumSet;

public class MudhorseEntity extends Animal {
    public static final EntityDataAccessor<Boolean> FORAGING = SynchedEntityData.defineId(MudhorseEntity.class, EntityDataSerializers.BOOLEAN);
    private LivingEntity commander;
    public int commanderSetTime;

    public MudhorseEntity(EntityType<? extends MudhorseEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(FTItems.SWAMP_MUCKER.get()), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new MudhorseForageGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new CommanderHurt(this));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30).add(Attributes.ATTACK_DAMAGE, 2).add(Attributes.MOVEMENT_SPEED, 0.22);
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
    public boolean doHurtTarget(Entity entityIn) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        boolean flag = entityIn.hurt(this.level().damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (flag) {
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, 0.3F, 0.0D));
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
        if (commanderSetTime > 0) {
            --commanderSetTime;
        } else {
            commander = null;
        }

        super.aiStep();
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
    public boolean canAttack(LivingEntity target) {
        return !(target instanceof MudhorseEntity) && super.canAttack(target);
    }

    @Override
    public void travel(Vec3 p_21280_) {
        float speedMod = isInWater() && getTarget() != null ? 1.5F : 1.0F;
        setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMod);

        super.travel(p_21280_);
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
                        if (targetMob instanceof TamableAnimal tamable) {
                            return !tamable.isTame() || tamable.getOwner() != livingentity;
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
