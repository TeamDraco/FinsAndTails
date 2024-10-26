package blueportal.finsandstails.common.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import blueportal.finsandstails.common.entities.ai.control.FTSmoothSwimmingMoveControl;
import blueportal.finsandstails.registry.FTItems;

import java.util.List;

import static net.minecraft.world.entity.EntitySelector.NO_CREATIVE_OR_SPECTATOR;

public class GopjetEntity extends AbstractFish {
    private static final EntityDataAccessor<Boolean> IS_BOOSTING = SynchedEntityData.defineId(GopjetEntity.class, EntityDataSerializers.BOOLEAN);
    private static final int BOOST_TIMER = 400;
    private int boostTimer = BOOST_TIMER;

    public GopjetEntity(EntityType<? extends GopjetEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 50);

    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, TealArrowfishEntity.class, 6, 1.0D, 1.5D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.45D);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BOOSTING, false);
    }

    @Override
    public void tick() {
        List<Player> list = level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(2.5D), NO_CREATIVE_OR_SPECTATOR);

        super.tick();
        if (boostTimer > 0) {
            --boostTimer;
        }
        if (boostTimer == 0 || !list.isEmpty() && !this.fromBucket() && this.isInWaterOrBubble()) {
            boostTimer = BOOST_TIMER;
            setBoosting(true);
            setDeltaMovement(calculateViewVector(this.getXRot(), this.getYRot()).multiply(1.7d, 0.0d, 1.7d));
        }
        if (boostTimer <= 350) {
            setBoosting(false);
        }
        level().broadcastEntityEvent(this, (byte)38);
        if (isBoosting()) {
            level().broadcastEntityEvent(this, (byte)39);
        }
    }

    public void setBoosting(boolean isBoosting) {
        this.getEntityData().set(IS_BOOSTING, isBoosting);
    }

    public boolean isBoosting() {
        return this.getEntityData().get(IS_BOOSTING);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.GOPJET_BUCKET.get());
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

    public void handleEntityEvent(byte id) {
        if (id == 38) {
            this.createParticles(ParticleTypes.BUBBLE, 1);
        }
        if (id == 39) {
            this.createParticles(ParticleTypes.BUBBLE, 4);
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void createParticles(SimpleParticleType p_208401_1_, int amount) {
        for (int i = 0; i < amount; i++) {
            double d0 = this.random.nextGaussian() * 0.056D;
            double d1 = this.random.nextGaussian() * 0.034D;
            double d2 = this.random.nextGaussian() * 0.025D;
            this.level().addParticle(p_208401_1_, this.getX(), this.getRandomY(), this.getZ(), d0, d1, d2);
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.GOPJET_SPAWN_EGG.get());
    }


    static class MoveHelperController extends FTSmoothSwimmingMoveControl {
        private final GopjetEntity gopjet;

        public MoveHelperController(GopjetEntity gopjetEntity) {
            super(gopjetEntity, 85, 10, 1.0F, 0.5F, true);
            this.gopjet = gopjetEntity;
        }

        public void tick() {
            if (this.gopjet.isInWater()) {
                this.gopjet.setDeltaMovement(this.gopjet.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == Operation.MOVE_TO && !this.gopjet.getNavigation().isDone()) {
                double d0 = this.wantedX - this.gopjet.getX();
                double d1 = this.wantedY - this.gopjet.getY();
                double d2 = this.wantedZ - this.gopjet.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.gopjet.setYRot(this.rotlerp(this.gopjet.getYRot(), f, 10.0F));
                    this.gopjet.yBodyRot = this.gopjet.getYRot();
                    this.gopjet.yHeadRot = this.gopjet.getYRot();
                    float f1 = (float)(this.speedModifier * this.gopjet.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.gopjet.isInWater()) {
                        this.gopjet.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double)(180F / (float)Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.gopjet.setXRot(this.rotlerp(this.gopjet.getXRot(), f2, 5.0F));
                        float f3 = Mth.cos(this.gopjet.getXRot() * ((float)Math.PI / 180F));
                        float f4 = Mth.sin(this.gopjet.getXRot() * ((float)Math.PI / 180F));
                        this.gopjet.zza = f3 * f1;
                        this.gopjet.yya = -f4 * f1;
                    } else {
                        this.gopjet.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.gopjet.setSpeed(0.0F);
                this.gopjet.setXxa(0.0F);
                this.gopjet.setYya(0.0F);
                this.gopjet.setZza(0.0F);
            }
        }
    }
}
