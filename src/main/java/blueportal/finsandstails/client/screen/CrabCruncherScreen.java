package blueportal.finsandstails.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.container.CrabCruncherContainer;

public class CrabCruncherScreen extends ItemCombinerScreen<CrabCruncherContainer> {
    private static final ResourceLocation CRUNCHER_GUI = new ResourceLocation(FinsAndTails.MOD_ID, "textures/gui/crab_cruncher.png");

    public CrabCruncherScreen(CrabCruncherContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, CRUNCHER_GUI);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 165;
        this.titleLabelX = 49;
    }

    @Override
    protected void renderErrorIcon(GuiGraphics guiGraphics, int p_266822_, int p_267045_) {
    }

}
