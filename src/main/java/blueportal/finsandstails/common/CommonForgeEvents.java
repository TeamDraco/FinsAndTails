package blueportal.finsandstails.common;

import blueportal.finsandstails.data.PlayerHitComboProvider;
import blueportal.finsandstails.network.FTMessages;
import blueportal.finsandstails.network.HitComboSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import blueportal.finsandstails.FTConfig;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.ai.base.IHydrate;
import blueportal.finsandstails.common.entities.PenglilEntity;
import blueportal.finsandstails.common.entities.WanderingSailorEntity;
import blueportal.finsandstails.common.entities.WherbleEntity;
import blueportal.finsandstails.common.entities.item.TealArrowfishArrowEntity;
import blueportal.finsandstails.registry.FTEnchantments;
import blueportal.finsandstails.registry.FTTags;

import java.util.List;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onArrowfishHit(ProjectileImpactEvent e) {
        Projectile proj = e.getProjectile();
        HitResult result = e.getRayTraceResult();

        if (result instanceof EntityHitResult hitResult && hitResult.getEntity() instanceof LivingEntity target && proj instanceof TealArrowfishArrowEntity fishArrow) {
            Entity owner = fishArrow.getOwner();

            if (owner instanceof Player player) {
                List<PenglilEntity> penglils = player.level().getNearbyEntities(PenglilEntity.class, TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(32.0D));

                for (PenglilEntity penglil : penglils) {

                    if (penglil.isTame() && penglil.getOwner() != null && !penglil.getOwner().equals(target)) {

                        if (target instanceof TamableAnimal tamable) {
                            boolean flag = tamable.isTame() && tamable.getOwner() != null && !tamable.getOwner().equals(owner);

                            if (flag) {
                                penglil.setTarget(target);
                            }
                            else {
                                return;
                            }
                        }
                        else {
                            penglil.setTarget(target);
                        }
                    }
                }

            }
        }

    }

    @SubscribeEvent
    public static void onPlayerAttackEntity(AttackEntityEvent e) {
        InteractionHand hand = InteractionHand.MAIN_HAND;
        Entity target = e.getTarget();
        Player player = e.getEntity();
        ItemStack mainhandItem = player.getItemInHand(hand);

        if (mainhandItem.is(FTTags.CLAW_GAUNTLETS)) {
            if (player instanceof ServerPlayer serverPlayer) {
                player.getCapability(PlayerHitComboProvider.HIT_COMBO).ifPresent(provider -> {
                    if (provider.getHitCombo() < 4) {
                        provider.setHitCombo(provider.getHitCombo() + 1);
                    }
                    else if (provider.getHitCombo() == 4) {
                        provider.setHitCombo(0);

                        target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.65D, 0.0D));
                        target.playSound(SoundEvents.PLAYER_LEVELUP);

                        for (int i = 0; i < 20; i++) {
                            if (target.level() instanceof ServerLevel sl) {
                                sl.sendParticles(ParticleTypes.CRIT, target.getRandomX(1.0D), target.getRandomY(), target.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                    FTMessages.sendToPlayer(new HitComboSyncS2CPacket(provider.getHitCombo()), serverPlayer);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        InteractionHand hand = InteractionHand.OFF_HAND;
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack offhandItem = player.getItemInHand(hand);

        if (offhandItem.is(FTTags.CLAW_GAUNTLETS)) {
            player.swing(hand);
            if (player instanceof ServerPlayer serverPlayer && target.hurt(player.level().damageSources().playerAttack(player), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                offhandItem.hurtAndBreak(1, player, (p_21301_) -> p_21301_.broadcastBreakEvent(EquipmentSlot.OFFHAND));

                player.getCapability(PlayerHitComboProvider.HIT_COMBO).ifPresent(provider -> {
                    if (provider.getHitCombo() < 4) {
                        provider.setHitCombo(provider.getHitCombo() + 1);
                    }
                    else if (provider.getHitCombo() == 4) {
                        provider.setHitCombo(0);

                        target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.65D, 0.0D));
                        target.playSound(SoundEvents.PLAYER_LEVELUP);

                        for (int i = 0; i < 20; i++) {
                            if (target.level() instanceof ServerLevel sl) {
                                sl.sendParticles(ParticleTypes.CRIT, target.getRandomX(1.0D), target.getRandomY(), target.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                    FTMessages.sendToPlayer(new HitComboSyncS2CPacket(provider.getHitCombo()), serverPlayer);
                });

                if (offhandItem.getEnchantmentLevel(FTEnchantments.UPPERCUTTING.get()) > 0) {
                    target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.3D, 0.0D));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickAir(PlayerInteractEvent event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        ItemStack offhandItem = player.getItemBySlot(EquipmentSlot.OFFHAND);

        if (offhandItem.is(FTTags.CLAW_GAUNTLETS)) {
            player.swing(hand);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Direction dir = event.getFace();

        if (player.isSecondaryUseActive() && player.getItemInHand(hand).isEmpty() && player.hasPassenger(e -> e instanceof PenglilEntity)) {
            PenglilEntity penglil = (PenglilEntity) player.getFirstPassenger();
            penglil.stopRiding();
            penglil.moveTo(pos.relative(dir), player.getYRot(), player.getXRot());
            player.swing(hand);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Wolf || entity instanceof Fox) {
            ((PathfinderMob) event.getEntity()).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((PathfinderMob) entity, WherbleEntity.class, true));
        }
        if (entity instanceof AbstractVillager) {
            ((PathfinderMob) event.getEntity()).goalSelector.addGoal(0, new LookAtPlayerGoal((PathfinderMob) entity, WanderingSailorEntity.class, 6.0F));
        }
    }

    @SubscribeEvent
    public static void onHitWaterPotion(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof ThrownPotion potionEntity) {
            ItemStack itemstack = potionEntity.getItem();
            Potion potion = PotionUtils.getPotion(itemstack);
            List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
            boolean flag = potion == Potions.WATER && list.isEmpty();
            if (flag) {
                applyWater(potionEntity);
            }
        }
    }

    private static void applyWater(Projectile projectile) {
        AABB aabb = projectile.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);

        for (LivingEntity livingEntity : projectile.level().getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (livingEntity instanceof IHydrate hydrate) {
                hydrate.rehydrate();
            }
        }

    }

    @SubscribeEvent
    public static void crabsFavorXPDrops(LivingExperienceDropEvent event) {
        LivingEntity attacker = event.getAttackingPlayer();

        if (attacker != null) {
            ItemStack heldItem = attacker.getMainHandItem();
            ItemStack heldItemOffhand = attacker.getOffhandItem();
            if (EnchantmentHelper.getEnchantments(heldItem).containsKey(FTEnchantments.CRABS_FAVOR.get()) && EnchantmentHelper.getEnchantments(heldItemOffhand).containsKey(FTEnchantments.CRABS_FAVOR.get())) {
                int i = EnchantmentHelper.getItemEnchantmentLevel(FTEnchantments.CRABS_FAVOR.get(), event.getAttackingPlayer().getItemInHand(InteractionHand.MAIN_HAND));
                event.setDroppedExperience(event.getOriginalExperience() * i + attacker.getCommandSenderWorld().random.nextInt(3));
            }
        }
    }

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        LootPool pool = event.getTable().getPool("main");
        if (name.equals(BuiltInLootTables.FISHING_FISH)) {
            if (FTConfig.Common.INSTANCE.finsFishingLoot.get()) {
                addEntry(pool, getInjectEntry(new ResourceLocation(FinsAndTails.MOD_ID, "inject/fishing"), 10, 1));
            }
        }
        if (name.equals(BuiltInLootTables.FISHERMAN_GIFT)) {
            addEntry(pool, getInjectEntry(new ResourceLocation("finsandtails:inject/fisherman_gift"), 15, 1));
        }
    }

    private static LootPoolEntryContainer getInjectEntry(ResourceLocation location, int weight, int quality) {
        return LootTableReference.lootTableReference(location).setWeight(weight).setQuality(quality).build();
    }

    private static void addEntry(LootPool pool, LootPoolEntryContainer entry) {
        LootPoolEntryContainer[] newEntries = new LootPoolEntryContainer[pool.entries.length + 1];
        System.arraycopy(pool.entries, 0, newEntries, 0, pool.entries.length);
        newEntries[pool.entries.length] = entry;

        pool.entries = newEntries;
    }
}
