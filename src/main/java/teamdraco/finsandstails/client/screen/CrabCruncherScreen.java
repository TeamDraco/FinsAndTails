package teamdraco.finsandstails.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.finsandstails.FinsAndTails;
import teamdraco.finsandstails.common.container.CrabCruncherContainer;

@OnlyIn(Dist.CLIENT)
public class CrabCruncherScreen extends AbstractContainerScreen<CrabCruncherContainer> {
    private static final ResourceLocation CRUNCHER_GUI = new ResourceLocation(FinsAndTails.MOD_ID, "textures/gui/crab_cruncher.png");

    public CrabCruncherScreen(CrabCruncherContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 165;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        Component textCompontent = Component.translatable("container.finsandtails.crab_cruncher");

        this.font.draw(matrixStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
        this.font.draw(matrixStack, textCompontent, ((float) imageWidth / 2F - font.width(textCompontent) / 2F), 18F, 4210752);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, CRUNCHER_GUI);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
