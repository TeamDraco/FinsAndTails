package blueportal.finsandstails.client;

import com.google.common.reflect.Reflection;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.*;
import blueportal.finsandstails.client.model.armor.GopjetpackModel;
import blueportal.finsandstails.client.render.*;
import blueportal.finsandstails.client.screen.CrabCruncherScreen;
import blueportal.finsandstails.client.screen.MudhorsePouchScreen;
import blueportal.finsandstails.network.TriggerFlyingPacket;
import blueportal.finsandstails.registry.*;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    private static final ResourceLocation HEARTS_TEXTURE = new ResourceLocation(FinsAndTails.MOD_ID, "textures/gui/icons.png");
    private static final ResourceLocation GAUNTLET_OVERLAY_TEXTURE = new ResourceLocation(FinsAndTails.MOD_ID, "textures/gui/overlay/gauntlet.png");
    private static final ResourceLocation GAUNTLET_BG_TEXTURE = new ResourceLocation(FinsAndTails.MOD_ID, "textures/gui/overlay/gauntlet_bg.png");

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FTModelLayers.BANDED_REDBACK_SHRIMP, BandedRedbackShrimpModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOLDEN_RIVER_RAY, GoldenRiverRayModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.MUDHORSE, MudhorseModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.ORNATE_BUGFISH, OrnateBugfishModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.PHANTOM_NUDIBRANCH, PhantomNudibranchModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.SWAMP_MUCKER, SwampMuckerModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.TEAL_ARROWFISH, TealArrowfishModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.TEAL_ARROWFISH_ARROW, TealArrowfishArrowModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.WHERBLE, WherbleModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.WHERBLING, WherbleModel::createWherblingBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOPJET, GopjetModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOPJETPACK, () -> GopjetpackModel.createArmorLayer(new CubeDeformation(0.0F)));
        event.registerLayerDefinition(FTModelLayers.WEE, WeeModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.PENGLIL, PenglilModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        Reflection.initialize(FTModelLayers.class);

        event.registerEntityRenderer(FTEntities.BANDED_REDBACK_SHRIMP.get(), BandedRedbackShrimpRenderer::new);
        event.registerEntityRenderer(FTEntities.GOLDEN_RIVER_RAY.get(), GoldenRiverRayRenderer::new);
        event.registerEntityRenderer(FTEntities.MUDHORSE.get(), MudhorseRenderer::new);
        event.registerEntityRenderer(FTEntities.ORNATE_BUGFISH.get(), OrnateBugfishRenderer::new);
        event.registerEntityRenderer(FTEntities.PHANTOM_NUDIBRANCH.get(), PhantomNudibranchRenderer::new);
        event.registerEntityRenderer(FTEntities.SWAMP_MUCKER.get(), SwampMuckerRenderer::new);
        event.registerEntityRenderer(FTEntities.TEAL_ARROWFISH_ARROW.get(), TealArrowfishArrowRenderer::new);
        event.registerEntityRenderer(FTEntities.TEAL_ARROWFISH.get(), TealArrowfishRenderer::new);
        event.registerEntityRenderer(FTEntities.WHERBLE.get(), WherbleRenderer::new);
        event.registerEntityRenderer(FTEntities.GOPJET.get(), GopjetRenderer::new);
        event.registerEntityRenderer(FTEntities.WEE.get(), WeeRenderer::new);
        event.registerEntityRenderer(FTEntities.PENGLIL.get(), PenglilRenderer::new);

        event.registerEntityRenderer(FTEntities.RED_BULL_CRAB.get(), RedBullCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.WHITE_BULL_CRAB.get(), WhiteBullCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.FLATBACK_LEAF_SNAIL.get(), FlatbackLeafSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.FLATBACK_SUCKER.get(), FlatbackSuckerRenderer::new);
        event.registerEntityRenderer(FTEntities.HIGH_FINNED_BLUE.get(), HighFinnedBlueRenderer::new);
        event.registerEntityRenderer(FTEntities.WEE_WEE.get(), WeeWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.NIGHT_LIGHT_SQUID.get(), NightlightSquidRenderer::new);
        event.registerEntityRenderer(FTEntities.PAPA_WEE.get(), PapaWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.RIVER_PEBBLE_SNAIL.get(), RiverPebbleSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.RUBBER_BELLY_GLIDER.get(), RubberBellyGliderRenderer::new);
        event.registerEntityRenderer(FTEntities.SIDEROL_WHISKERED_SNAIL.get(), SiderolWhiskeredSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.VIBRA_WEE.get(), VibraWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.WANDERING_SAILOR.get(), WanderingSailorRenderer::new);
        event.registerEntityRenderer(FTEntities.SPINDLY_GEM_CRAB.get(), SpindlyGemCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.CROWNED_HORATEE.get(), CrownedHorateeRenderer::new);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        MenuScreens.register(FTContainers.MUDHORSE_POUCH.get(), MudhorsePouchScreen::new);
        MenuScreens.register(FTContainers.CRAB_CRUNCHER.get(), CrabCruncherScreen::new);
        event.enqueueWork(FTItemProperties::setupItemProperties);
    }

    @Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeBus {
        private static boolean wasJumping;

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Minecraft minecraft = Minecraft.getInstance();
                final LocalPlayer player = minecraft.player;
                if (player != null && (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == FTItems.GOPJET_JETPACK.get() || player.getItemBySlot(EquipmentSlot.CHEST).getItem() == FTItems.ARMORED_GOPJET_JETPACK.get())) {
                    boolean jumping = minecraft.options.keyJump.isDown();
                    if (jumping != wasJumping) {
                        TriggerFlyingPacket packet = new TriggerFlyingPacket(jumping);
                        packet.handle(player);
                        FinsAndTails.NETWORK.sendToServer(packet);
                    }
                    wasJumping = jumping;
                }
            }
        }

        @SubscribeEvent
        public static void playerGuiRender(RenderGuiOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST); // todo - curios compat

            if (!chest.isEmpty() && event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type() && !player.isCreative() && !player.isSpectator()) {
                CharmType charm = CharmType.getCharm(player);
                if (charm == null) return;

                PoseStack poseStack = event.getGuiGraphics().pose();

                poseStack.pushPose();
                RenderSystem.enableBlend();

                renderPlayerHealth(event.getGuiGraphics(), player, charm);

                RenderSystem.disableBlend();
                poseStack.popPose();
            }

            InteractionHand offhand = InteractionHand.OFF_HAND;
            InteractionHand mainhand = InteractionHand.MAIN_HAND;

            boolean hasGauntlets = player.getItemInHand(mainhand).is(FTTags.CLAW_GAUNTLETS) && player.getItemInHand(offhand).is(FTTags.CLAW_GAUNTLETS);

            if (hasGauntlets && event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type() && !player.isSpectator()) {
                PoseStack poseStack = event.getGuiGraphics().pose();

                poseStack.pushPose();
                RenderSystem.enableBlend();

                renderGauntletOverlay(event.getGuiGraphics());

                RenderSystem.disableBlend();
                poseStack.popPose();
            }

        }

        // todo - make the combo ACTUALLY require a left-right combo
        protected static void renderGauntletOverlay(GuiGraphics guiGraphics) {
            //Gui gui = Minecraft.getInstance().gui;
            //int x = gui.screenWidth / 2 - 14;
            //int y = gui.screenHeight / 2 + 9;

            //guiGraphics.blit(GAUNTLET_BG_TEXTURE, x, y, 0, 0, 27, 9);

            //int hitCombo = ClientHitComboData.getHitCombo();
            //switch (hitCombo) {
            //    case 1 -> guiGraphics.blit(GAUNTLET_OVERLAY_TEXTURE, x, y, 0, 0, 7, 7);
            //    case 2 -> guiGraphics.blit(GAUNTLET_OVERLAY_TEXTURE, x, y, 0, 0, 14, 7);
            //    case 3 -> guiGraphics.blit(GAUNTLET_OVERLAY_TEXTURE, x, y, 0, 0, 20, 7);
            //    case 4 -> guiGraphics.blit(GAUNTLET_OVERLAY_TEXTURE, x, y, 0, 0, 28, 7);
            //}
        }

        private static void renderPlayerHealth(GuiGraphics guiGraphics, Player player, CharmType charm) {
            Gui gui = Minecraft.getInstance().gui;

            if (player != null) {
                int health = Mth.ceil(player.getHealth());

                health = Mth.clamp(health, 0, 8);
                boolean highlight = gui.healthBlinkTime > (long) gui.tickCount && (gui.healthBlinkTime - (long) gui.tickCount) / 3L % 2L == 1L;
                if (health < gui.lastHealth && player.invulnerableTime > 0) {
                    gui.lastHealthTime = Util.getMillis();
                    gui.healthBlinkTime = (gui.tickCount + 20);
                } else if (health > gui.lastHealth && player.invulnerableTime > 0) {
                    gui.lastHealthTime = Util.getMillis();
                    gui.healthBlinkTime = gui.tickCount + 10;
                }

                if (Util.getMillis() - gui.lastHealthTime > 1000L) {
                    gui.displayHealth = health;
                    gui.lastHealthTime = Util.getMillis();
                }

                gui.lastHealth = health;
                int healthLast = gui.displayHealth;

                int x = gui.screenWidth / 2 - 91;
                int y = gui.screenHeight - 39;
                float healthMax = Math.max((float) player.getAttributeValue(Attributes.MAX_HEALTH), (float) Math.max(healthLast, health));
                int absorption = Mth.ceil(player.getAbsorptionAmount());
                int healthRows = Mth.ceil(healthMax / 20.0F);
                int rowHeight = Math.max(10 - (healthRows - 2), 3);
                int regen = -1;

                gui.random.setSeed(gui.tickCount * 312871);
                if (player.hasEffect(MobEffects.REGENERATION)) {
                    regen = gui.tickCount % Mth.ceil(healthMax + 5.0F);
                }

                gui.minecraft.getProfiler().popPush("health");
                renderHearts(guiGraphics, charm, x, y, rowHeight, regen, healthMax, health, absorption, highlight);
            }
        }

        public static void renderHearts(GuiGraphics stack, CharmType charm, int x, int y, int rowHeight, int regen, float healthMax, int health, int absorption, boolean highlight) {
            Gui gui = Minecraft.getInstance().gui;

            int heartCount = Mth.ceil((double) healthMax / 2.0D);
            int absorptionHeartCount = Mth.ceil((double) absorption / 2.0D);

            for (int heart = heartCount + absorptionHeartCount - 1; heart >= 0; --heart) {
                int level = heart / 10;
                int index = heart % 10;
                int heartX = x + index * 8;
                int heartY = y - level * rowHeight;

                if (health + absorption <= 4) {
                    heartY += gui.random.nextInt(2);
                }

                if (heart < heartCount && heart == regen) {
                    heartY -= 2;
                }

                int halfHeartIndex = heart * 2;
                if (highlight && halfHeartIndex < absorption) {
                    renderHeart(stack, charm, heartX, heartY, true, halfHeartIndex + 1 == absorption);
                }

                if (halfHeartIndex < health) {
                    renderHeart(stack, charm, heartX, heartY, false, halfHeartIndex + 1 == health);
                }
            }
        }

        private static void renderHeart(GuiGraphics pGuiGraphics, CharmType type, int x, int y, boolean highlight, boolean halfHeart) {
            pGuiGraphics.blit(HEARTS_TEXTURE, x, y, type.getX(halfHeart, highlight), 0, 9, 9);
        }

        public enum CharmType {
            RUBY,
            EMERALD,
            AMBER,
            PEARL,
            SAPPHIRE,
            GEM;

            public int getX(boolean halfHeart, boolean highlight) {
                // todo use highlight to change texture?
                if (halfHeart) return ordinal() * 18 + 9;
                return ordinal() * 18;
            }

            static CharmType getCharm(Player player) {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

                if (stack.is(FTItems.SPINDLY_RUBY_CHARM.get())) return RUBY;
                else if (stack.is(FTItems.SPINDLY_EMERALD_CHARM.get())) return EMERALD;
                else if (stack.is(FTItems.SPINDLY_AMBER_CHARM.get())) return AMBER;
                else if (stack.is(FTItems.SPINDLY_PEARL_CHARM.get())) return PEARL;
                else if (stack.is(FTItems.SPINDLY_SAPPHIRE_CHARM.get())) return SAPPHIRE;
                else if (stack.is(FTItems.GEM_CRAB_AMULET.get())) return GEM;

                return null;
            }
        }
    }
}
