package ga.justreddy.wiki.bedwars.action;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;

/**
 * @author JustReddy
 */
public abstract class Action {

    public abstract String getId();

    public abstract void onAction(BedWars bedWars, GamePlayer gamePlayer, Object data);

}
