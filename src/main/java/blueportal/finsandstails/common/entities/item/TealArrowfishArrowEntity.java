package blueportal.finsandstails.common.entities.item;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.common.items.TealArrowfishItem;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;

public class TealArrowfishArrowEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    private final TealArrowfishItem arrow;

    public TealArrowfishArrowEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
        super(type, worldIn);
        this.arrow = (TealArrowfishItem) FTItems.TEAL_ARROWFISH.get();
    }

    public TealArrowfishArrowEntity(Level worldIn, Item item) {
        super(FTEntities.TEAL_ARROWFISH_ARROW.get(), worldIn);
        this.arrow = (TealArrowfishItem) item;
    }

    public TealArrowfishArrowEntity(Level worldIn, double x, double y, double z) {
        this(FTEntities.TEAL_ARROWFISH_ARROW.get(), worldIn);
        setPos(x, y, z);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(arrow);
    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        Entity shooter = getOwner();
        buffer.writeInt(shooter == null ? 0 : shooter.getId());
        buffer.writeVarInt(Item.getId(arrow));
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
