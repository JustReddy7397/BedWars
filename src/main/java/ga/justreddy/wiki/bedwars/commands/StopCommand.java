package ga.justreddy.wiki.bedwars.commands;

import ga.justreddy.wiki.bedwars.BedWars;

import java.util.List;

/**
 * @author JustReddy
 */
public class StopCommand extends BedWarsCommand {

    public StopCommand(BedWars plugin, String name, boolean requiresPlayer, String permission, List<String> aliases) {
        super(plugin, name, false, "bedwars.stop", aliases);
    }

    @Override
    public boolean execute(Executor executor) {

        // TODO Custom IMPL for stopping the server

        return true;
    }
}
