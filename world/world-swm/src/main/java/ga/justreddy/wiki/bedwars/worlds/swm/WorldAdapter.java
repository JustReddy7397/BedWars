package ga.justreddy.wiki.bedwars.worlds.swm;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.game.Game;
import ga.justreddy.wiki.bedwars.model.game.ResetAdapter;
import org.bukkit.World;

/**
 * @author JustReddy
 */
public class WorldAdapter extends ResetAdapter {

    public WorldAdapter(BedWars plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(Game game) {

    }

    @Override
    public void onReset(Game game) {

    }

    @Override
    public void onDisable(Game game) {

    }

    @Override
    public World getWorld() {
        return null;
    }
}
