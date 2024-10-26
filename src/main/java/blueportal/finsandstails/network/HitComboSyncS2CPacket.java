package blueportal.finsandstails.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import blueportal.finsandstails.client.ClientHitComboData;

import java.util.function.Supplier;

public class HitComboSyncS2CPacket {
    private final int hit_combo;

    public HitComboSyncS2CPacket(int hit_combo) {
        this.hit_combo = hit_combo;
    }

    public HitComboSyncS2CPacket(FriendlyByteBuf buf) {
        this.hit_combo = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(hit_combo);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientHitComboData.set(hit_combo));
        return;
    }
}