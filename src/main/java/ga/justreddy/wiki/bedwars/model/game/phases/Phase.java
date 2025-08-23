package ga.justreddy.wiki.bedwars.model.game.phases;

import ga.justreddy.wiki.bedwars.model.game.Game;

/**
 * @author JustReddy
 */
public interface Phase {

    void onEnable(Game game);

    void onTick(Game game);

    void onDisable(Game game);

    Phase nextPhase();
}
