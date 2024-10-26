package blueportal.finsandstails.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlayerHitComboProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerHitComboData> HIT_COMBO = CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerHitComboData hitCombo = null;
    private final LazyOptional<PlayerHitComboData> opt = LazyOptional.of(this::createHitCombo);

    @Nonnull
    private PlayerHitComboData createHitCombo() {
        if (hitCombo == null) {
            hitCombo = new PlayerHitComboData();
        }
        return hitCombo;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == HIT_COMBO) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createHitCombo().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createHitCombo().loadNBTData(tag);
    }
}
