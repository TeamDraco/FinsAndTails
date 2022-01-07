package teamdraco.finsandstails.common.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import teamdraco.finsandstails.registry.FTItems;
import teamdraco.finsandstails.registry.FtSounds;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class WanderingSailorEntity extends AbstractVillagerEntity implements IMerchant {
    public static final Int2ObjectMap<VillagerTrades.ITrade[]> TRADES = toIntMap(ImmutableMap.of(
            1, new VillagerTrades.ITrade[]{new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_EMERALD.get()), new ItemStack(FTItems.BANDED_REDBACK_SHRIMP_BUCKET.get()), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_RUBY.get(), 4), new ItemStack(FTItems.GOPJET_JET.get()), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_AMBER.get(), 4), new ItemStack(FTItems.FWIN.get(), 1), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_EMERALD.get(), 2), new ItemStack(FTItems.WHITE_BULL_CRAB_CLAW.get(), 2), 3, 3, 30)},
            2, new VillagerTrades.ITrade[]{new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_SAPPHIRE.get()), new ItemStack(FTItems.NIGHT_LIGHT_SQUID_TENTACLE.get(), 5), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_PEARL.get()), new ItemStack(FTItems.PAPA_WEE_BUCKET.get()), 3, 3, 30)}));

    public WanderingSailorEntity(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.75F));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(5, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.2F;
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() != FTItems.WANDERING_SAILOR_SPAWN_EGG.get() && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (p_230254_2_ == Hand.MAIN_HAND) {
                p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            } else {
                if (!this.level.isClientSide) {
                    this.setTradingPlayer(p_230254_1_);
                    this.openTradingScreen(p_230254_1_, this.getDisplayName(), 0);
                }

                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FTItems.WANDERING_SAILOR_SPAWN_EGG.get());
    }

    @Override
    protected void updateTrades() {
        VillagerTrades.ITrade[] trade1 = TRADES.get(1);
        VillagerTrades.ITrade[] trade2 = TRADES.get(2);
        if (trade1 != null && trade2 != null) {
            MerchantOffers offers = this.getOffers();
            this.addOffersFromItemListings(offers, trade1, 2);
            int i = this.random.nextInt(trade2.length);
            VillagerTrades.ITrade villagertrades$itrade = trade2[i];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.random);
            if (merchantoffer != null) {
                offers.add(merchantoffer);
            }

        }
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        if (offer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? FtSounds.WANDERING_SAILOR_TRADE.get() : FtSounds.WANDERING_SAILOR_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FtSounds.WANDERING_SAILOR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FtSounds.WANDERING_SAILOR_DEATH.get();
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean p_213721_1_) {
        return p_213721_1_ ? FtSounds.WANDERING_SAILOR_YES.get() : FtSounds.WANDERING_SAILOR_NO.get();
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return FtSounds.WANDERING_SAILOR_YES.get();
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    private static class ItemsForItemsTrade implements VillagerTrades.ITrade {
        private final ItemStack buying1, buying2, selling;
        private final int maxUses, xp;
        private final float priceMultiplier;
        public static final Map<Integer, ItemStack> GEMS = Util.make(Maps.newHashMap(), (hashMap) -> {
            hashMap.put(0, new ItemStack(FTItems.SPINDLY_EMERALD.get()));
            hashMap.put(1, new ItemStack(FTItems.SPINDLY_AMBER.get()));
            hashMap.put(2, new ItemStack(FTItems.SPINDLY_PEARL.get()));
            hashMap.put(3, new ItemStack(FTItems.SPINDLY_RUBY.get()));
            hashMap.put(4, new ItemStack(FTItems.SPINDLY_SAPPHIRE.get()));
        });

        public ItemsForItemsTrade(ItemStack buying1, ItemStack buying2, ItemStack selling, int maxUses, int xp, float priceMultiplier) {
            this.buying1 = buying1;
            this.buying2 = buying2;
            this.selling = selling;
            this.maxUses = maxUses;
            this.xp = xp;
            this.priceMultiplier = priceMultiplier;
        }

        public ItemsForItemsTrade(ItemStack buying1, ItemStack selling, int maxUses, int xp, float priceMultiplier) {
            this(buying1, ItemStack.EMPTY, selling, maxUses, xp, priceMultiplier);
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            ItemStack stack = GEMS.get(rand.nextInt(GEMS.size()));
            return new MerchantOffer(buying1, buying2, selling, maxUses, xp, priceMultiplier);
        }
    }
}
