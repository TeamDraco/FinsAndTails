package blueportal.finsandstails.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class TriggerFlyingPacket implements INetworkPacket {
    private final boolean flying;

    public TriggerFlyingPacket(FriendlyByteBuf buffer) {
        flying = buffer.readBoolean();
    }

    public TriggerFlyingPacket(boolean flying) {
        this.flying = flying;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(flying);
    }

    @Override
    public void handle(Player player) {
        player.getPersistentData().putBoolean("FinsFlying", flying);
    }
}
