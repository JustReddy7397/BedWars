package ga.justreddy.wiki.bedwars.commands.bedwars;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.commands.BedWarsCommand;
import ga.justreddy.wiki.bedwars.commands.Executor;
import ga.justreddy.wiki.bedwars.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class MainBedWarsCommand extends BedWarsCommand {

    private final Map<String, SubCommand> commands;

    public MainBedWarsCommand(BedWars plugin) {
        super(plugin, "bedwars", true, null, "bw");
        this.commands = new HashMap<>();
    }

    @Override
    public boolean execute(Executor executor) {

        return true;
    }

    private void addCommand(SubCommand command) {
        this.commands.put(command.getName(), command);
    }

}
