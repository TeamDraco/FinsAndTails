package blueportal.finsandstails.data;

import net.minecraft.nbt.CompoundTag;

public class PlayerHitComboData {
    private int hitCombo;

    public int getHitCombo() {
        return hitCombo;
    }

    public void setHitCombo(int hitCombo) {
        this.hitCombo = hitCombo;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("playerHitCombo", hitCombo);
    }

    public void loadNBTData(CompoundTag tag) {
        hitCombo = tag.getInt("playerHitCombo");
    }
}

