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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import blueportal.finsandstails.common.criterion.FTCriterion;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WherbleEntity extends Animal implements Bucketable {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WherbleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(WherbleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_PROJECTILE = SynchedEntityData.defineId(WherbleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> THROWER = SynchedEntityData.defineId(WherbleEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public WherbleEntity(EntityType<? extends Animal> p_i48568_1_, Level p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.35D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(Items.BEETROOT), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Fox.class, 8.0F, 1.0D, 1.15D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 1.0D, 1.15D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8).add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    public static boolean checkWherbleSpawnRules(EntityType<? extends WherbleEntity> p_223316_0_, LevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return p_223316_1_.getBlockState(p_223316_3_.below()).is(Blocks.GRASS_BLOCK) && p_223316_1_.getRawBrightness(p_223316_3_, 0) > 8;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(IS_PROJECTILE, false);
        this.entityData.define(THROWER, Optional.empty());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source != this.level().damageSources().freeze() && super.hurt(source, amount);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public UUID getThrower() {
        return this.entityData.get(THROWER).orElse(null);
    }

    public void setThrower(UUID uuid) {
        this.entityData.set(THROWER, Optional.ofNullable(uuid));
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

    @Override
    public void tick() {
        super.tick();

        if (isProjectile()) {
            List<Entity> entities = level().getEntities(this, getBoundingBox(), Entity::canBeHitByProjectile);
            for (var entity : entities) {
                if (getBoundingBox().intersects(entity.getBoundingBox()) && entity.hurt(damageSources().mobProjectile(this, getThrower() != null ? level().getPlayerByUUID(getThrower()) : this), 1.0F)) {
                    setProjectile(false);
                }
                if (entity instanceof Player player && player.getUUID().equals(UUID.fromString("c7e2fbc4-e21e-40be-b8e1-8ac69ad53416"))) {
                    System.out.println(entity.getUUID());
                    if (level().getPlayerByUUID(getThrower()) instanceof ServerPlayer serverPlayer) FTCriterion.THROW_WHERBLING_IN_THE_VOID.trigger(serverPlayer);
                }
            }

        }

        if (onGround() || isInWater()) {
            setProjectile(false);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (isBaby() && heldItem.isEmpty() && this.isAlive() && !this.isProjectile()) {
            ItemStack bucket = getBucketItemStack();
            Level level = level();

            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucket);
            }
            saveToBucketTag(bucket);

            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, bucket);
            } else if (!player.getInventory().add(bucket)) {
                player.drop(bucket, false);
            }

            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    public void shoot(double p_37266_, double p_37267_, double p_37268_, float scale, float p_37270_) {
        Vec3 vec3 = (new Vec3(p_37266_, p_37267_, p_37268_)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double)p_37270_), this.random.triangle(0.0D, 0.0172275D * (double)p_37270_), this.random.triangle(0.0D, 0.0172275D * (double)p_37270_)).scale(scale);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void shootFromRotation(@Nullable Entity entity, float p_37253_, float p_37254_, float p_37255_, float scale, float inaccuracy) {
        float f = -Mth.sin(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((p_37253_ + p_37255_) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, scale, inaccuracy);
        if (entity != null) {
            Vec3 vec3 = entity.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, entity.onGround() ? 0.0D : vec3.y, vec3.z));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag == null) {
            setVariant(random.nextInt(6));
        }
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMobGroupData(1);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.BEETROOT;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return FTSounds.WHERBLE_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FTSounds.WHERBLE_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return FTSounds.WHERBLE_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        WherbleEntity wherble = FTEntities.WHERBLE.get().create(p_241840_1_);
        wherble.setVariant(random.nextInt(4));
        return wherble;
    }

    @Override
    public void push(Entity entity) {
        if (isProjectile()) {
            setProjectile(false);
        }
        super.push(entity);
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        super.actuallyHurt(damageSource, amount);

        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD) && this.isProjectile() && amount > this.getHealth()) {
            Player player = level().getPlayerByUUID(getThrower());
            if (player instanceof ServerPlayer serverPlayer) FTCriterion.THROW_WHERBLING_IN_THE_VOID.trigger(serverPlayer);
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(FTItems.WHERBLE_SPAWN_EGG.get());
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.2F : 0.4F;
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public boolean isProjectile() {
        return this.entityData.get(IS_PROJECTILE);
    }

    public void setProjectile(boolean projectile) {
        this.entityData.set(IS_PROJECTILE, projectile);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.put("WherbleData", serializeNBT());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.WHERBLING.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return FTSounds.WHERBLE_AMBIENT.get();
    }
}
