package blueportal.finsandstails.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractDescriptionItem extends Item {
    public AbstractDescriptionItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc").withStyle(ChatFormatting.DARK_AQUA));
        } else {
            components.add(Component.translatable("finsandtails.info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
