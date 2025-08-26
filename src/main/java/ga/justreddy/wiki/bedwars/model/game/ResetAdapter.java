package ga.justreddy.wiki.bedwars.model.game;

import ga.justreddy.wiki.bedwars.BedWars;
import org.bukkit.World;

/**
 * @author JustReddy
 */
public abstract class ResetAdapter {

    private final BedWars plugin;

    public ResetAdapter(BedWars plugin) {
        this.plugin = plugin;
    }

    public abstract void onEnable(Game game);

    public abstract void onReset(Game game);

    public abstract void onDisable(Game game);

    public abstract World getWorld();

}
