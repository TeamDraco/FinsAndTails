package blueportal.finsandstails.common.entities;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.common.entities.ai.goals.WeeHurtByEntityGoal;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTTags;

import java.util.List;

public class WeeEntity extends AbstractSchoolingFish {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WeeEntity.class, EntityDataSerializers.INT);

    public WeeEntity(EntityType<? extends WeeEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, TealArrowfishEntity.class, 6, 1.0D, 1.5D));
        this.goalSelector.addGoal(1, new WeeHurtByEntityGoal(this));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnDataIn, @javax.annotation.Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int variant;
        Holder<Biome> holder = worldIn.getBiome(this.blockPosition());

        if (reason == MobSpawnType.SPAWN_EGG) {
            variant = random.nextInt(3);
        }
        else if (dataTag == null) {
            if (holder.is(BiomeTags.IS_JUNGLE)) variant = 1;
            else if (holder.is(FTTags.MUCK_WEE_SPAWNS)) variant = 2;
            else variant = 0;

        } else {
            if (dataTag.contains("Variant", 3)){
                variant = dataTag.getInt("Variant");
            } else variant = 0;
        }
        this.setVariant(variant);
        return spawnDataIn;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
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

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(FTItems.WEE_BUCKET.get());
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

    @Override
    public void tick() {
        super.tick();
        if (random.nextInt(2500) == 0 && shouldSpawnPapaWee()) {
            PapaWeeEntity papaWee = FTEntities.PAPA_WEE.get().create(level());
            papaWee.setPos(this.getX(), this.getY(), this.getZ());

            level().addFreshEntity(papaWee);
        }
    }

    private boolean shouldSpawnPapaWee() {
        List<WeeEntity> weeList = this.level().getEntitiesOfClass(WeeEntity.class, this.getBoundingBox().inflate(8.0D));
        List<PapaWeeEntity> papaWeeList = this.level().getEntitiesOfClass(PapaWeeEntity.class, this.getBoundingBox().inflate(16.0D));
        return weeList.size() >= 10 && papaWeeList.isEmpty();
    }
}
