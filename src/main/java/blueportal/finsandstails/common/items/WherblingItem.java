package blueportal.finsandstails.common.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import blueportal.finsandstails.common.criterion.FTCriterion;
import blueportal.finsandstails.common.entities.WherbleEntity;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTSounds;

import java.util.UUID;

public class WherblingItem extends MobBucketItem {

    public WherblingItem(Item.Properties p_43140_) {
        super(FTEntities.WHERBLE, () -> Fluids.EMPTY, () -> SoundEvents.EMPTY, p_43140_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.playSound(FTSounds.WHERBLE_THROW.get(), 1.5F,  (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + 1.5F );

        if (!level.isClientSide) {
            WherbleEntity wherble = new WherbleEntity(FTEntities.WHERBLE.get(), level);
            UUID id = wherble.getUUID();
            wherble.deserializeNBT(itemstack.getOrCreateTag().getCompound("WherbleData"));
            wherble.setUUID(id);
            wherble.moveTo(player.getEyePosition());
            wherble.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            wherble.setProjectile(true);
            wherble.setThrower(player.getUUID());

            if (!wherble.isBaby()) {
                wherble.setBaby(true);
                wherble.setAge(-24000);
            }
            level.addFreshEntity(wherble);
        }
        player.getCooldowns().addCooldown(this, 10);
        if (player instanceof ServerPlayer serverPlayer) FTCriterion.THROW_WHERBLING.trigger(serverPlayer);

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}