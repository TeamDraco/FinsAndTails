package blueportal.finsandstails.common.entities;

import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.common.entities.ai.base.IHydrate;
import blueportal.finsandstails.common.entities.ai.goals.ShareTheBubbleGoal;
import blueportal.finsandstails.common.entities.ai.goals.SwimWithoutGroundGoal;
import blueportal.finsandstails.common.entities.ai.goals.WalkWithGroundGoal;
import blueportal.finsandstails.common.entities.ai.control.SmoothWalkGroundAndSwimMoveControl;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CrownedHorateeEntity extends Animal implements GeoEntity, IHydrate, Bucketable {
	private static final EntityDataAccessor<Boolean> HAS_BABY = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BUBBLE_CHARGE = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.BOOLEAN);

	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	private static final EntityDataAccessor<Integer> BUBBLE_TARGET = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrownedHorateeEntity.class, EntityDataSerializers.BOOLEAN);


	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.SEA_PICKLE);

	public CrownedHorateeEntity(EntityType<? extends CrownedHorateeEntity> p_27523_, Level p_27524_) {
		super(p_27523_, p_27524_);
		this.moveControl = new SmoothWalkGroundAndSwimMoveControl(this, 85, 10, 0.5F, 1.0F, false);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.setCanPickUpLoot(true);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	@Override
	public float maxUpStep() {
		return 1.0F;
	}

	public MobType getMobType() {
		return MobType.WATER;
	}

	public boolean checkSpawnObstruction(LevelReader p_30348_) {
		return p_30348_.isUnobstructed(this);
	}

	public int getAmbientSoundInterval() {
		return 120;
	}

	public void baseTick() {
		int i = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply(i);
	}

	public boolean isPushedByFluid() {
		return false;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15D, true));
		this.goalSelector.addGoal(2, new CrownedBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LayBabyGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 0.85F, FOOD_ITEMS, false));
		this.goalSelector.addGoal(6, new ShareTheBubbleGoal(this, 1.0D, 10));
		this.goalSelector.addGoal(7, new GoToWaterGoal(this, 1.0F));
		this.goalSelector.addGoal(8, new SwimWithoutGroundGoal(this));
		this.goalSelector.addGoal(9, new WalkWithGroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new BabyTargetGoal<>(this, RedBullCrabEntity.class));
		this.targetSelector.addGoal(1, new BabyTargetGoal<>(this, SpindlyGemCrabEntity.class));
		this.targetSelector.addGoal(1, new BabyTargetGoal<>(this, WhiteBullCrabEntity.class));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_BABY, false);
		this.entityData.define(BUBBLE_CHARGE, false);
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
		this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
		this.entityData.define(BUBBLE_TARGET, 0);
		this.entityData.define(FROM_BUCKET, false);
	}

	public void setBubbleTarget(int p_32818_) {
		this.entityData.set(BUBBLE_TARGET, p_32818_);
	}

	public boolean hasBubbleTarget() {
		return this.entityData.get(BUBBLE_TARGET) != 0;
	}

	@Nullable
	public Entity getBubbleTarget() {
		return this.level().getEntity(this.entityData.get(BUBBLE_TARGET));
	}

	public List<UUID> getTrustedUUIDs() {
		List<UUID> list = Lists.newArrayList();
		list.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse((UUID) null));
		list.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse((UUID) null));
		return list;
	}

	public void addTrustedUUID(@Nullable UUID p_28516_) {
		if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
			this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(p_28516_));
		} else {
			this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(p_28516_));
		}
	}

	public boolean trusts(UUID p_28530_) {
		return this.getTrustedUUIDs().contains(p_28530_);
	}

	public boolean hasBaby() {
		return this.entityData.get(HAS_BABY);
	}

	public void setHasBaby(boolean p_30235_) {
		this.entityData.set(HAS_BABY, p_30235_);
	}

	public boolean isBubbleCharge() {
		return this.entityData.get(BUBBLE_CHARGE);
	}

	public void setBubbleCharge(boolean p_30235_) {
		this.entityData.set(BUBBLE_CHARGE, p_30235_);
	}

	@Override
	public boolean canAttack(LivingEntity p_21171_) {
		return !this.trusts(p_21171_.getUUID()) && super.canAttack(p_21171_);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isBubbleCharge() && this.hasBubbleTarget()) {
			if (this.getBubbleTarget() != null) {
				for (int i = 0; i < 3; ++i) {
					this.level().addParticle(ParticleTypes.BUBBLE, this.getX() + this.getLookAngle().x / 2.0D, this.getEyeY(), this.getZ() + this.getLookAngle().z / 2.0D, (this.getBubbleTarget().getX() - (this.getX() + this.getLookAngle().x / 2.0D)) * 0.95F, (this.getBubbleTarget().getEyeY() - this.getEyeY()) * 0.95F, (this.getBubbleTarget().getZ() - (this.getZ() + this.getLookAngle().z / 2.0D)) * 0.95F);
				}
			}
		}
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return FOOD_ITEMS.test(p_27600_);
	}

	public InteractionResult mobInteract(Player p_28153_, InteractionHand p_28154_) {
		ItemStack itemstack = p_28153_.getItemInHand(p_28154_);
		Item item = itemstack.getItem();

		if (this.trusts(p_28153_.getUUID()) && this.isFood(itemstack)) {
			heal(2);
			itemstack.shrink(1);
			return InteractionResult.SUCCESS;
		}

		if (this.isBaby()) {
			return Bucketable.bucketMobPickup(p_28153_, p_28154_, this).orElse(super.mobInteract(p_28153_, p_28154_));
		}
		return super.mobInteract(p_28153_, p_28154_);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_30176_) {
		super.addAdditionalSaveData(p_30176_);
		p_30176_.putBoolean("HasBaby", this.hasBaby());
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listtag = new ListTag();

		for (UUID uuid : list) {
			if (uuid != null) {
				listtag.add(NbtUtils.createUUID(uuid));
			}
		}

		p_30176_.put("Trusted", listtag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_30162_) {
		super.readAdditionalSaveData(p_30162_);
		this.setHasBaby(p_30162_.getBoolean("HasBaby"));
		ListTag listtag = p_30162_.getList("Trusted", 11);

		for (int i = 0; i < listtag.size(); ++i) {
			this.addTrustedUUID(NbtUtils.loadUUID(listtag.get(i)));
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	public float getWalkTargetValue(BlockPos p_30159_, LevelReader p_30160_) {
		return CrownedHorateeEntity.onSandOrGravel(p_30160_, p_30159_) ? 10.0F + p_30160_.getLightLevelDependentMagicValue(p_30159_) - 0.5F : p_30160_.getLightLevelDependentMagicValue(p_30159_) - 0.5F;
	}

	public static boolean checkCrownedSpawnRules(EntityType<? extends CrownedHorateeEntity> p_30179_, LevelAccessor p_30180_, MobSpawnType p_30181_, BlockPos p_30182_, RandomSource p_30183_) {
		return p_30182_.getY() < p_30180_.getSeaLevel() + 4 && CrownedHorateeEntity.onSandOrGravel(p_30180_, p_30182_) && isBrightEnoughToSpawn(p_30180_, p_30182_);
	}

	public static boolean onSandOrGravel(BlockGetter p_57763_, BlockPos p_57764_) {
		return isSandOrGravel(p_57763_, p_57764_.below());
	}

	public static boolean isSandOrGravel(BlockGetter p_57801_, BlockPos p_57802_) {
		return p_57801_.getBlockState(p_57802_).is(BlockTags.SAND) || p_57801_.getBlockState(p_57802_).is(Tags.Blocks.GRAVEL);
	}

	public void travel(Vec3 p_27490_) {
		if (this.isEffectiveAi() && this.isInWater() && !this.onGround()) {
			this.moveRelative(0.05F, p_27490_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		}
		else {
			super.travel(p_27490_);
		}
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		GroundPathNavigation groundPathNavigation = new GroundPathNavigation(this, level());
		groundPathNavigation.setCanFloat(false);
		return groundPathNavigation;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	protected void handleAirSupply(int p_149194_) {
		if (this.isAlive() && !this.isInWaterRainOrBubble() && !this.isBaby()) {
			this.setAirSupply(p_149194_ - 1);
			if (this.getAirSupply() == -20) {
				this.setAirSupply(0);
				this.hurt(this.level().damageSources().dryOut(), 2.0F);
			}
		} else {
			this.setAirSupply(this.getMaxAirSupply());
		}
	}

	public void rehydrate() {
		int i = this.getAirSupply() + 1200;
		this.setAirSupply(Math.min(i, this.getMaxAirSupply()));
	}

	public int getMaxAirSupply() {
		return 6000;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
		controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
		controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "miscController", 0, this::miscPredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return FTSounds.HORATEE_AMBIENT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return FTSounds.HORATEE_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return FTSounds.HORATEE_DEATH.get();
	}

	private <E extends GeoAnimatable> PlayState miscPredicate(AnimationState<E> event) {
		if (this.isBubbleCharge()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.bubble"));
		} else {
			return PlayState.STOP;
		}
		return PlayState.CONTINUE;
	}

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
		float f = 1.0F;

		if (this.isBaby()) {
			f += 0.5F;
		}

		if (event.getLimbSwingAmount() > 0.01F) {
			if (this.isInWater()) {
				if (this.onGround()) {
					f += 0.25F;
					event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.walk"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.swim"));
				}
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.crawl"));
			}
		} else {
			if (this.isInWater()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.idle_water"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.idle_land"));
			}
		}
		event.getController().setAnimationSpeed(f);
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public CrownedHorateeEntity getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FTEntities.CROWNED_HORATEE.get().create(p_146743_);
	}

	public boolean canFallInLove() {
		return super.canFallInLove() && !this.hasBaby();
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean p_149196_) {
		this.entityData.set(FROM_BUCKET, p_149196_);
	}

	@Override
	public void saveToBucketTag(ItemStack p_27494_) {
		Bucketable.saveDefaultDataToBucketTag(this, p_27494_);
		CompoundTag compoundtag = p_27494_.getOrCreateTag();
		compoundtag.putInt("Age", this.getAge());
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listtag = new ListTag();

		for (UUID uuid : list) {
			if (uuid != null) {
				listtag.add(NbtUtils.createUUID(uuid));
			}
		}

		compoundtag.put("Trusted", listtag);
	}

	public void loadFromBucketTag(CompoundTag p_148708_) {
		Bucketable.loadDefaultDataFromBucketTag(this, p_148708_);
		if (p_148708_.contains("Age")) {
			this.setAge(p_148708_.getInt("Age"));
		}
		if (p_148708_.contains("Trusted")) {
			ListTag listtag = p_148708_.getList("Trusted", 11);

			for (int i = 0; i < listtag.size(); ++i) {
				this.addTrustedUUID(NbtUtils.loadUUID(listtag.get(i)));
			}
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
		if (p_146748_ == MobSpawnType.BUCKET && p_146750_ != null && p_146750_.contains("Age", 3)) {
			this.setAge(p_146750_.getInt("Age"));
			return p_146749_;
		} else if (p_146748_ == MobSpawnType.BUCKET) {
			this.setBaby(true);
			return p_146749_;
		} else {

			return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
		}
	}

	@Override
	public ItemStack getBucketItemStack() {
		return FTItems.BABY_HORATEE_BUCKET.get().getDefaultInstance();
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_FISH;
	}

	static class CrownedBreedGoal extends BreedGoal {
		private final CrownedHorateeEntity crownedHorateeEntity;

		CrownedBreedGoal(CrownedHorateeEntity p_30244_, double p_30245_) {
			super(p_30244_, p_30245_);
			this.crownedHorateeEntity = p_30244_;
		}

		public boolean canUse() {
			return super.canUse() && !this.crownedHorateeEntity.hasBaby();
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && !this.crownedHorateeEntity.hasBaby();
		}

		protected void breed() {
			ServerPlayer serverplayer = this.animal.getLoveCause();
			if (serverplayer == null && this.partner.getLoveCause() != null) {
				serverplayer = this.partner.getLoveCause();
			}

			if (serverplayer != null) {
				serverplayer.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this.animal, this.partner, null);
			}

			this.level.broadcastEntityEvent(this.animal, (byte) 18);
			this.crownedHorateeEntity.setAge(6000);
			this.partner.setAge(6000);
			this.animal.resetLove();
			this.partner.resetLove();
			this.crownedHorateeEntity.setHasBaby(true);
		}
	}

	static class LayBabyGoal extends MoveToBlockGoal {
		private final CrownedHorateeEntity crownedHorateeEntity;
		private int layCounter;

		LayBabyGoal(CrownedHorateeEntity p_30276_, double p_30277_) {
			super(p_30276_, p_30277_, 16);
			this.crownedHorateeEntity = p_30276_;
		}

		public boolean canUse() {
			return this.crownedHorateeEntity.hasBaby() ? super.canUse() : false;
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && this.crownedHorateeEntity.hasBaby();
		}

		@Override
		public void start() {
			super.start();
			this.layCounter = 0;
		}

		public void tick() {
			super.tick();
			if (!this.crownedHorateeEntity.isInWater() && this.isReachedTarget() && ++this.layCounter == this.adjustedTickDelay(1)) {
				if (this.crownedHorateeEntity.level() instanceof ServerLevel serverLevel) {
					CrownedHorateeEntity ageablemob = this.crownedHorateeEntity.getBreedOffspring(serverLevel, this.crownedHorateeEntity);
					ageablemob.setBaby(true);
					ServerPlayer serverplayer = this.crownedHorateeEntity.getLoveCause();
					if (serverplayer != null) {
						ageablemob.addTrustedUUID(serverplayer.getUUID());
					}
					ageablemob.moveTo(this.crownedHorateeEntity.getX(), this.crownedHorateeEntity.getY(), this.crownedHorateeEntity.getZ(), 0.0F, 0.0F);
					serverLevel.addFreshEntityWithPassengers(ageablemob);
					serverLevel.broadcastEntityEvent(this.crownedHorateeEntity, (byte) 18);
					if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
						serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, this.crownedHorateeEntity.getX(), this.crownedHorateeEntity.getY(), this.crownedHorateeEntity.getZ(), this.crownedHorateeEntity.getRandom().nextInt(7) + 1));
					}
				}
				this.crownedHorateeEntity.setHasBaby(false);
			}
		}

		protected boolean isValidTarget(LevelReader level, BlockPos pos) {
			return (level.isEmptyBlock(pos.above()) || !level.isEmptyBlock(pos) || !level.getFluidState(pos).isEmpty()) && CrownedHorateeEntity.onSandOrGravel(level, pos);
		}

	}

	static class GoToWaterGoal extends Goal {
		private final CrownedHorateeEntity mob;
		private double wantedX;
		private double wantedY;
		private double wantedZ;
		private final double speedModifier;
		private final Level level;

		public GoToWaterGoal(CrownedHorateeEntity p_32425_, double p_32426_) {
			this.mob = p_32425_;
			this.speedModifier = p_32426_;
			this.level = p_32425_.level();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (this.mob.getAirSupply() > 1200) {
				return false;
			} else if (this.mob.isInWater()) {
				return false;
			} else {
				Vec3 vec3 = this.getWaterPos();
				if (vec3 == null) {
					return false;
				} else {
					this.wantedX = vec3.x;
					this.wantedY = vec3.y;
					this.wantedZ = vec3.z;
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return !this.mob.getNavigation().isDone();
		}

		public void start() {
			this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}

		@javax.annotation.Nullable
		private Vec3 getWaterPos() {
			RandomSource random = this.mob.getRandom();
			BlockPos blockpos = this.mob.blockPosition();

			for (int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
				if (this.level.getBlockState(blockpos1).is(Blocks.WATER)) {
					return Vec3.atBottomCenterOf(blockpos1);
				}
			}

			return null;
		}
	}

	static class BabyTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

		public BabyTargetGoal(Mob p_26060_, Class<T> p_26061_) {
			super(p_26060_, p_26061_, true);
		}

		public boolean canUse() {
			return !this.mob.isBaby() ? false : super.canUse();
		}
	}
}
