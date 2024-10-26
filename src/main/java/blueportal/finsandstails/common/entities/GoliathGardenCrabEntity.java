package blueportal.finsandstails.common.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import blueportal.finsandstails.registry.FTSounds;

import javax.annotation.Nullable;

public class GoliathGardenCrabEntity extends WaterAnimal {
    private int attackAnimationTick;

    public GoliathGardenCrabEntity(EntityType<? extends GoliathGardenCrabEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, RedBullCrabEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D, 1500));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Villager.class, 6.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9f;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.KNOCKBACK_RESISTANCE, 0.8D).add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity p_70652_1_) {
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = p_70652_1_.hurt(this.level().damageSources().mobAttack(this), f1);
        if (flag) {
            p_70652_1_.setDeltaMovement(p_70652_1_.getDeltaMovement().add(0.0D, (double)0.4F, 0.0D));
            this.doEnchantDamageEffects(this, p_70652_1_);
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
    }

    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 4) {
            this.attackAnimationTick = 20;
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        }
        else {
            super.handleEntityEvent(p_70103_1_);
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
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

/*    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.GOLIATH_GARDEN_CRAB_SPAWN_EGG.get());
    }*/

    @Override
    protected void handleAirSupply(int p_209207_1_) {
    }

    static class MoveHelperController extends MoveControl {
        private final GoliathGardenCrabEntity crab;

        MoveHelperController(GoliathGardenCrabEntity crab) {
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