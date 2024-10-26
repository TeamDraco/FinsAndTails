package blueportal.finsandstails.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.render.item.FTArmorRenderProperties;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTSounds;

import java.util.List;
import java.util.Random;

public class GopjetpackItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new FinsArmorMaterial(FinsAndTails.MOD_ID + ":gopjet_jetpack", 0, new int[]{0, 0, 0, 0}, 1, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(FTItems.GOPJET_JET.get()));
    private final Random random = new Random();
    private int bubbleSoundTime;

    public GopjetpackItem() {
        super(MATERIAL, Type.CHESTPLATE, new Properties().stacksTo(1).durability(128));
    }

    public BlockPos getBlockUnderPlayer(Player player) {
        final BlockPos.MutableBlockPos position = player.blockPosition().mutable();
        BlockState state;
        while ((!(state = player.level().getBlockState(position)).blocksMotion() && state.getFluidState().isEmpty()) || state.getBlock() instanceof LeavesBlock) {
            position.move(Direction.DOWN);
            if (position.getY() <= 0) return null;
        }
        return position;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (stack.getMaxDamage() - stack.getDamageValue() > 1 || player.isCreative()) {
            boolean canFly = world.isRainingAt(player.blockPosition());
            int flyingTicksRemaining = 0;
            int stackIndex = -1;
            BlockPos pos = getBlockUnderPlayer(player);

            if (!canFly) {
                if (pos != null)
                    if (player.blockPosition().getY() > 0 && world.getBlockState(pos).is(Blocks.WATER)) {
                        canFly = true;
                    } else {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack inventoryStack = player.getInventory().getItem(i);
                            Item item = inventoryStack.getItem();
                            int ticksJumping = inventoryStack.hasTag() ? inventoryStack.getTag().getInt("FinsFlyingTicks") : 0;
                            if (item == Items.WATER_BUCKET) {
                                flyingTicksRemaining = 100 - ticksJumping;
                            } else if (item == Items.POTION && PotionUtils.getPotion(inventoryStack) == Potions.WATER) {
                                flyingTicksRemaining = 30 - ticksJumping;
                            } else {
                                continue;
                            }
                            stackIndex = i;
                            canFly = true;
                            break;
                        }
                    }
            }

            CompoundTag persistentData = player.getPersistentData();
            if (persistentData.getBoolean("FinsFlying")) {
                if (pos != null) {
                    if (canFly || player.blockPosition().getY() > 0 && world.getBlockState(pos).is(Blocks.WATER)) {

                        player.fallDistance = 0;
                        int ticksJumping = persistentData.getInt("FinsFlyingTicks") + 1;
                        if (ticksJumping % 10 == 0) {
                            stack.hurtAndBreak(1, player, Player -> Player.broadcastBreakEvent(EquipmentSlot.CHEST));
                        }
                        persistentData.putInt("FinsFlyingTicks", ticksJumping);
                        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.1, 0));

                        /*Vec3 d3 = player.getViewVector(1.0F).scale(0.35F);
                        if (!player.isOnGround()) {
                            Vec3 vec31 = Vec3.ZERO;
                            player.setDeltaMovement(vec31.add(d3));
                            player.setPose(Pose.SWIMMING);
                            player.setSwimming(true);
                            player.startFallFlying();
                            player.resetFallDistance();
                        }
                        player.stopFallFlying();*/
                    }
                    if (canFly || player.blockPosition().getY() > 0 && world.getBlockState(pos).is(Blocks.WATER)) {
                        if (random.nextInt(100) < this.bubbleSoundTime++) {
                            this.bubbleSoundTime = 0;
                            world.playSound(player, player.blockPosition(), FTSounds.JETPACK_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                        }

                        if (world.isClientSide) {
                            for (int i = 0; i < 4; i++) {
                                float sign = Math.signum(i - 2);
                                if (sign == 0) {
                                    sign = 1;
                                }
                                double playerRotation = Math.toRadians(player.yBodyRot + 35 * sign);
                                double xOffset = random.nextGaussian() * 0.05;
                                double yOffset = random.nextGaussian() * 0.01;
                                double zOffset = random.nextGaussian() * 0.05;
                                double xPos = player.getX() + xOffset - Math.sin(-playerRotation) * 0.35;
                                double yPos = player.getY() + yOffset + 0.7;
                                double zPos = player.getZ() + zOffset - Math.cos(playerRotation) * 0.35;
                                for (int j = 0; j <= 8; j++) {
                                    world.addParticle(random.nextInt(2) == 0 ? ParticleTypes.SPLASH : ParticleTypes.BUBBLE, xPos, yPos, zPos, 0, -0.10, 0);
                                }
                            }
                        }
                        if (stackIndex != -1) {
                            ItemStack flyingStack = player.getInventory().getItem(stackIndex);
                            if (flyingTicksRemaining - 1 <= 0) {
                                Item item = flyingStack.getItem();
                                flyingStack.shrink(1);
                                ItemStack newStack = null;
                                if (item == Items.WATER_BUCKET) {
                                    newStack = new ItemStack(Items.BUCKET);
                                } else if (item == Items.POTION && PotionUtils.getPotion(flyingStack) == Potions.WATER) {
                                    newStack = new ItemStack(Items.GLASS_BOTTLE);
                                } else if (Block.byItem(item) == Blocks.WET_SPONGE) {
                                    newStack = new ItemStack(Blocks.SPONGE);
                                }
                                if (newStack != null) {
                                    if (flyingStack.isEmpty()) {
                                        player.getInventory().setItem(stackIndex, newStack);
                                    } else if (!player.getInventory().add(newStack)) {
                                        player.drop(newStack, false);
                                    }
                                    flyingStack.getOrCreateTag().remove("FinsFlyingTicks");
                                }
                            } else {
                                CompoundTag tag = flyingStack.getOrCreateTag();
                                tag.putInt("FinsFlyingTicks", tag.getInt("FinsFlyingTicks") + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new FTArmorRenderProperties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc.2").withStyle(ChatFormatting.DARK_AQUA));
        } else {
            components.add(Component.translatable("finsandtails.info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.UNBREAKING && super.canApplyAtEnchantingTable(stack, enchantment);
    }

}
