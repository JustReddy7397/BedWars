package ga.justreddy.wiki.bedwars.model.game.phases;

import ga.justreddy.wiki.bedwars.model.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * @author JustReddy
 */
public class PhaseManager {

    private final Game game;
    private Phase currentPhase;

    public PhaseManager(Game game) {
        this.game = game;
        this.currentPhase = null; // Initialize with no phase
    }

    public boolean isInPhase(@NotNull Phase phase) {
        return phase.getClass().equals(this.currentPhase.getClass());
    }

    public void nextPhase() {
        Phase phase = currentPhase.nextPhase();
        if (phase == null) return;
        setPhase(phase);
    }

    public void setPhase(Phase phase) {
        currentPhase = phase;
        currentPhase.onEnable(game);
    }

    public void onTick() {
        if (currentPhase == null) return;
        currentPhase.onTick(game);
    }



}
