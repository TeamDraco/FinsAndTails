package blueportal.finsandstails.common.entities;

import blueportal.finsandstails.registry.FTEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import blueportal.finsandstails.common.entities.ai.base.SchoolingTamableAnimal;
import blueportal.finsandstails.common.entities.ai.control.GroundAndSwimmerNavigator;
import blueportal.finsandstails.common.entities.ai.goals.TamableFollowLeaderGoal;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PenglilEntity extends SchoolingTamableAnimal implements Bucketable {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PenglilEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(PenglilEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(PenglilEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(PenglilEntity.class, EntityDataSerializers.BOOLEAN);

    public PenglilEntity(EntityType<? extends PenglilEntity> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 45);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.15D, Ingredient.of(FTItems.HIGH_FINNED_BLUE.get()), false));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PapaWeeEntity.class, 8.0F, 1.6D, 1.4D));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D, 10) {
            @Override
            public boolean canUse() {
                return !isInSittingPose() && !this.mob.isInWater() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1) {
            @Override
            public boolean canUse() {
                return !isInSittingPose() && super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(3, new MorningGiftGoal(this));
        this.goalSelector.addGoal(5, new TamableFollowLeaderGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WeeEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, BandedRedbackShrimpEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, HighFinnedBlueEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WeeWeeEntity.class, true));
    }

    @Override
    public int getMaxSchoolSize() {
        return 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(FTItems.HIGH_FINNED_BLUE.get());
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return new GroundAndSwimmerNavigator(this, level());
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static boolean canPenglilSpawn(EntityType<? extends TamableAnimal> penglil, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return worldIn.getBlockState(pos.below()).getBlock() == Blocks.SAND && worldIn.getRawBrightness(pos, 0) > 8;
    }

    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
        }
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    @Nonnull
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.PENGLIL_BUCKET.get());
    }

    @Override
    @Nonnull
    public SoundEvent getPickupSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        Item item = heldItem.getItem();
        ItemStack itemstack1 = new ItemStack(FTItems.PENGLIL_BUCKET.get());
        InteractionResult actionresulttype = super.mobInteract(player, hand);

        if (level().isClientSide()) {
            return InteractionResult.CONSUME;
        }
        else {
            if (heldItem.getItem() == Items.BUCKET && this.isAlive() && !this.isOrderedToSit()) {
                playSound(this.getPickupSound(), 1.0F, 1.0F);
                heldItem.shrink(1);
                this.saveToBucketTag(itemstack1);
                if (!this.level().isClientSide) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
                }
                if (heldItem.isEmpty()) {
                    player.setItemInHand(hand, itemstack1);
                } else if (!player.getInventory().add(itemstack1)) {
                    player.drop(itemstack1, false);
                }
                this.discard();
                return InteractionResult.SUCCESS;
            }

            float maxHealth = this.getMaxHealth();
            float health = this.getHealth();
            if (heldItem.getItem() == FTItems.WEE.get() && health < maxHealth) {
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                heal(2);
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
                return InteractionResult.SUCCESS;
            }

            if (item == FTItems.WEE.get() && !this.isTame()) {
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(!isInWater());
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;            }
            if (this.isOwnedBy(player) && item != FTItems.HIGH_FINNED_BLUE.get() && !isInWater()) {
                setOrderedToSit(!isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                return InteractionResult.SUCCESS;
            }
        }

        return actionresulttype;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        if (isTame()) {
            compoundnbt.putUUID("Owner", getOwnerUUID());
        }
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        this.setVariant(compoundTag.getInt("Variant"));
        if (compoundTag.contains("Owner")) {
            this.setOwnerUUID(compoundTag.getUUID("Owner"));
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isInSittingPose() && !isInWater()) {
            if (getNavigation().getPath() != null) {
                getNavigation().stop();
            }

            travelVector = Vec3.ZERO;
        }
        else {
            if (this.isEffectiveAi() && !this.isInWater()) {
                float speedMod = getTarget() != null && getTarget().isAlive() ? 2.5F : 1.0F;
                this.setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMod);
            }
            if (this.isEffectiveAi() && this.isInWater() && !isInSittingPose()) {
                this.moveRelative(0.1F, travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            }
        }
        super.travel(travelVector);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(IS_LYING, false);
        this.entityData.define(RELAX_STATE_ONE, false);
        this.entityData.define(FROM_BUCKET, false);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(int variant) {
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (dataTag == null) {
            setVariant(random.nextInt(8));
        } else {
            if (dataTag.contains("Variant", 3)){
                this.setVariant(dataTag.getInt("Variant"));
            }
            if (dataTag.hasUUID("Owner")) {
                this.setOwnerUUID(dataTag.getUUID("Owner"));
                this.setTame(true);
            }
        }
        return spawnDataIn;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob p_241840_2_) {
        return FTEntities.PENGLIL.get().create(level);
    }

    protected SoundEvent getAmbientSound() {
        return FTSounds.PENGLIL_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return FTSounds.PENGLIL_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FTSounds.PENGLIL_HURT.get();
    }

    public boolean isLying() {
        return this.entityData.get(IS_LYING);
    }

    public void setLying(boolean p_213419_1_) {
        this.entityData.set(IS_LYING, p_213419_1_);
    }

    public void setRelaxStateOne(boolean p_213415_1_) {
        this.entityData.set(RELAX_STATE_ONE, p_213415_1_);
    }

    public boolean isRelaxStateOne() {
        return this.entityData.get(RELAX_STATE_ONE);
    }

    @Override
    public void tick() {
        super.tick();

        if (isInWater()) {
            setOrderedToSit(false);
        }
    }

    static class MorningGiftGoal extends Goal {
        private final PenglilEntity penglil;
        private Player owner;
        private BlockPos bedPos;
        private int tickCounter;

        public MorningGiftGoal(PenglilEntity catIn) {
            this.penglil = catIn;
        }

        public boolean canUse() {
            if (!this.penglil.isTame()) {
                return false;
            } else if (this.penglil.isOrderedToSit()) {
                return false;
            } else {
                LivingEntity livingentity = this.penglil.getOwner();
                if (livingentity instanceof Player) {
                    this.owner = (Player)livingentity;
                    if (!livingentity.isSleeping()) {
                        return false;
                    }

                    if (this.penglil.distanceToSqr(this.owner) > 100.0D) {
                        return false;
                    }

                    BlockPos blockpos = this.owner.blockPosition();
                    BlockState blockstate = this.penglil.level().getBlockState(blockpos);
                    if (blockstate.is(BlockTags.BEDS)) {
                        this.bedPos = blockstate.getOptionalValue(BedBlock.FACING).map((p_234186_1_) -> blockpos.relative(p_234186_1_.getOpposite())).orElseGet(() -> new BlockPos(blockpos));
                        return !this.spaceIsOccupied();
                    }
                }

                return false;
            }
        }

        private boolean spaceIsOccupied() {
            for(PenglilEntity penglilentity : this.penglil.level().getEntitiesOfClass(PenglilEntity.class, (new AABB(this.bedPos)).inflate(2.0D))) {
                if (penglilentity != this.penglil && (penglilentity.isLying() || penglilentity.isRelaxStateOne())) {
                    return true;
                }
            }

            return false;
        }

        public boolean canContinueToUse() {
            return this.penglil.isTame() && !this.penglil.isOrderedToSit() && this.owner != null && this.owner.isSleeping() && this.bedPos != null && !this.spaceIsOccupied();
        }

        public void start() {
            if (this.bedPos != null) {
                this.penglil.setOrderedToSit(false);
                this.penglil.getNavigation().moveTo(this.bedPos.getX(), this.bedPos.getY(), this.bedPos.getZ(), 1.1F);
            }

        }

        public void stop() {
            this.penglil.setLying(false);
            float f = this.penglil.level().getTimeOfDay(1.0F);
            if (this.owner.getSleepTimer() >= 100 && (double)f > 0.77D && (double)f < 0.8D && (double)this.penglil.level().getRandom().nextFloat() < 0.5D) {
                this.giveMorningGift();
            }

            this.tickCounter = 0;
            this.penglil.setRelaxStateOne(false);
            this.penglil.getNavigation().stop();
        }

        private void giveMorningGift() {
            RandomSource random = this.penglil.getRandom();
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            mutable.set(this.penglil.blockPosition());
            this.penglil.randomTeleport((double)(mutable.getX() + random.nextInt(11) - 5), (double)(mutable.getY() + random.nextInt(5) - 2), (double)(mutable.getZ() + random.nextInt(11) - 5), false);
            mutable.set(this.penglil.blockPosition());
            LootTable loottable = this.penglil.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) this.penglil.level())).withParameter(LootContextParams.ORIGIN, this.penglil.position()).withParameter(LootContextParams.THIS_ENTITY, this.penglil);

            for(ItemStack itemstack : loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.GIFT))) {
                this.penglil.level().addFreshEntity(new ItemEntity(this.penglil.level(), (double)mutable.getX() - (double)Mth.sin(this.penglil.yBodyRot * ((float)Math.PI / 180F)), (double)mutable.getY(), (double)mutable.getZ() + (double)Mth.cos(this.penglil.yBodyRot * ((float)Math.PI / 180F)), itemstack));
            }

        }

        public void tick() {
            if (this.owner != null && this.bedPos != null) {
                this.penglil.setOrderedToSit(false);
                this.penglil.getNavigation().moveTo(this.bedPos.getX(), this.bedPos.getY(), this.bedPos.getZ(), 1.1F);
                if (this.penglil.distanceToSqr(this.owner) < 2.5D) {
                    ++this.tickCounter;
                    if (this.tickCounter > 16) {
                        this.penglil.setLying(true);
                        this.penglil.setRelaxStateOne(false);
                    } else {
                        this.penglil.lookAt(this.owner, 45.0F, 45.0F);
                        this.penglil.setRelaxStateOne(true);
                    }
                } else {
                    this.penglil.setLying(false);
                }
            }

        }
    }

    static class MoveHelperController extends MoveControl {
        private final PenglilEntity penglil;

        MoveHelperController(PenglilEntity penglil) {
            super(penglil);
            this.penglil = penglil;
        }

        private void updateSpeed() {
            if (this.penglil.isInWater()) {
                this.penglil.setDeltaMovement(this.penglil.getDeltaMovement().add(0.0D, 0.0005D, 0.0D));

                if (this.penglil.isBaby()) {
                    this.penglil.setSpeed(Math.max(this.penglil.getSpeed() / 3.0F, 0.06F));
                }
            }
            else if (this.penglil.onGround()) {
                this.penglil.setSpeed(Math.max(this.penglil.getSpeed(), 0.06F));
            }
        }

        public void tick() {
            if (penglil.isInSittingPose()) return;
            this.updateSpeed();
            if (this.operation == Operation.MOVE_TO && !this.penglil.getNavigation().isDone()) {
                double d0 = this.wantedX - this.penglil.getX();
                double d1 = this.wantedY - this.penglil.getY();
                double d2 = this.wantedZ - this.penglil.getZ();
                double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
                d1 = d1 / d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.penglil.setYRot(this.rotlerp(this.penglil.getYRot(), f, 90.0F));
                this.penglil.yBodyRot = this.penglil.getYRot();
                float f1 = (float)(this.speedModifier * this.penglil.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.penglil.setSpeed(Mth.lerp(0.125F, this.penglil.getSpeed(), f1));
                this.penglil.setDeltaMovement(this.penglil.getDeltaMovement().add(0.0D, (double)this.penglil.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.penglil.setSpeed(0.0F);
            }
        }
    }
}