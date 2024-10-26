package blueportal.finsandstails.client;

public class ClientHitComboData {
    private static int hitCombo;

    public static void set(int hitCombo) {
        ClientHitComboData.hitCombo = hitCombo;
    }

    public static int getHitCombo() {
        return hitCombo;
    }
}