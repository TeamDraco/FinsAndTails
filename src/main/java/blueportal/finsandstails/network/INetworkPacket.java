package blueportal.finsandstails.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import blueportal.finsandstails.FinsAndTails;

public interface INetworkPacket {
    static SimpleChannel makeChannel(String name, String version) {
        return NetworkRegistry.newSimpleChannel(new ResourceLocation(FinsAndTails.MOD_ID, name), () -> version, version::equals, version::equals);
    }

    default void write(FriendlyByteBuf buffer) {}
    void handle(Player player);
}
