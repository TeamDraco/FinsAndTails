package blueportal.finsandstails.common.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import javax.annotation.Nullable;
import java.util.Map;

public class WanderingSailorEntity extends AbstractVillager implements Merchant {
    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> TRADES = toIntMap(ImmutableMap.of(
            1, new VillagerTrades.ItemListing[]{new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_EMERALD.get()), new ItemStack(FTItems.BANDED_REDBACK_SHRIMP_BUCKET.get()), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_RUBY.get(), 4), new ItemStack(FTItems.GOPJET_JET.get()), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_AMBER.get(), 4), new ItemStack(FTItems.FWIN.get(), 1), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_EMERALD.get(), 2), new ItemStack(FTItems.WHITE_BULL_CRAB_CLAW.get(), 2), 3, 3, 30)},
            2, new VillagerTrades.ItemListing[]{new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_SAPPHIRE.get()), new ItemStack(FTItems.NIGHT_LIGHT_SQUID_TENTACLE.get(), 5), 3, 3, 30), new ItemsForItemsTrade(new ItemStack(FTItems.SPINDLY_PEARL.get()), new ItemStack(FTItems.PAPA_WEE_BUCKET.get()), 3, 3, 30)}));

    public WanderingSailorEntity(EntityType<? extends AbstractVillager> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.75F));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
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
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 1.2F;
    }

    @Override
    public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (/*itemstack.getItem() != FTItems.WANDERING_SAILOR_SPAWN_EGG.get() && */this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (p_230254_2_ == InteractionHand.MAIN_HAND) {
                p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                if (!this.level().isClientSide) {
                    this.setTradingPlayer(p_230254_1_);
                    this.openTradingScreen(p_230254_1_, this.getDisplayName(), 0);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return /*new ItemStack(FTItems.WANDERING_SAILOR_SPAWN_EGG.get())*/ null;
    }

    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] trade1 = TRADES.get(1);
        VillagerTrades.ItemListing[] trade2 = TRADES.get(2);
        if (trade1 != null && trade2 != null) {
            MerchantOffers offers = this.getOffers();
            this.addOffersFromItemListings(offers, trade1, 2);
            int i = this.random.nextInt(trade2.length);
            VillagerTrades.ItemListing villagertrades$itrade = trade2[i];
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
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? FTSounds.WANDERING_SAILOR_TRADE.get() : FTSounds.WANDERING_SAILOR_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FTSounds.WANDERING_SAILOR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FTSounds.WANDERING_SAILOR_DEATH.get();
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean p_213721_1_) {
        return p_213721_1_ ? FTSounds.WANDERING_SAILOR_YES.get() : FTSounds.WANDERING_SAILOR_NO.get();
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return FTSounds.WANDERING_SAILOR_YES.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return null;
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    private static class ItemsForItemsTrade implements VillagerTrades.ItemListing {
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
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemStack stack = GEMS.get(rand.nextInt(GEMS.size()));
            return new MerchantOffer(buying1, buying2, selling, maxUses, xp, priceMultiplier);
        }
    }
}
