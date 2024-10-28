package blueportal.finsandstails.common.entities.ai.base;

public interface IKillCooldown {

    int getKillCooldown();

    default int incrementCooldown() {
        return getKillCooldown() - 1;
    }
}
