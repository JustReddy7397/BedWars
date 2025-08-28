package ga.justreddy.wiki.bedwars.commands.bedwars;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.commands.BedWarsCommand;
import ga.justreddy.wiki.bedwars.commands.Executor;
import ga.justreddy.wiki.bedwars.commands.SubCommand;
import ga.justreddy.wiki.bedwars.commands.bedwars.admin.AdminCommand;
import ga.justreddy.wiki.bedwars.commands.bedwars.admin.CreateCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author JustReddy
 */
public class MainBedWarsCommand extends BedWarsCommand {

    private final Map<String, SubCommand<MainBedWarsCommand>> commands;

    public MainBedWarsCommand(BedWars plugin) {
        super(plugin, "bedwars", true, null, "bw");
        this.commands = new HashMap<>();
        addCommand(new AdminCommand(plugin));
    }

    @Override
    public boolean execute(Executor executor) {

        if (!executor.hasArgs()) {
            // TODO Display help message
            return true;
        }

        Optional<SubCommand<MainBedWarsCommand>> optionalSubCommand = getCommand(executor.getArg(0));
        if (!optionalSubCommand.isPresent()) {
            // TODO Display error message for unknown command
            executor.getSender().sendMessage("Unknown command: " + executor.getArg(0));
            return true;
        }

        final SubCommand<MainBedWarsCommand> subCommand = optionalSubCommand.get();
        if (subCommand.getPermission() != null && !executor.getSender().hasPermission(subCommand.getPermission())) {
            // TODO Display error message for insufficient permissions
            executor.getSender().sendMessage("You do not have permission to use this command: " + subCommand.getName());
            return true;
        }

        return subCommand.execute(executor.stripArgs(1));
    }

    private void addCommand(SubCommand<MainBedWarsCommand> command) {
        this.commands.put(command.getName(), command);
    }

    private Optional<SubCommand<MainBedWarsCommand>> getCommand(String name) {
        if (this.commands.containsKey(name)) {
            return Optional.of(this.commands.get(name));
        }
        return commands.values().stream().filter(c -> c.getName().equalsIgnoreCase(name) || c.getAliases().contains(name)).findFirst();
    }

}
