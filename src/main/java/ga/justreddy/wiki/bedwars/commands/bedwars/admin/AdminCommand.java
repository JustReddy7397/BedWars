package ga.justreddy.wiki.bedwars.commands.bedwars.admin;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.commands.Executor;
import ga.justreddy.wiki.bedwars.commands.SubCommand;
import ga.justreddy.wiki.bedwars.commands.bedwars.MainBedWarsCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author JustReddy
 */
public class AdminCommand extends SubCommand<MainBedWarsCommand> {

    private final Map<String, SubCommand<AdminCommand>> commands;

    public AdminCommand(BedWars plugin) {
        super(plugin, "admin", "Admin commands", "/bw admin", "bedwars.bw.admin");
        this.commands = new HashMap<>();
        addCommand(new CreateCommand(plugin));
    }

    @Override
    public boolean execute(Executor executor) {

        if (!executor.hasArgs()) {
            // TODO Display help message for admin commands
            executor.getSender().sendMessage("Available admin commands: " + String.join(", ", commands.keySet()));
            return true;
        }

        Optional<SubCommand<AdminCommand>> optionalSubCommand = getCommand(executor.getArg(0));
        if (optionalSubCommand.isEmpty()) {
            // TODO Display error message for unknown command
            executor.getSender().sendMessage("Unknown admin command: " + executor.getArg(0));
            return true;
        }

        final SubCommand subCommand = optionalSubCommand.get();
        if (subCommand.getPermission() != null && !executor.getSender().hasPermission(subCommand.getPermission())) {
            // TODO Display error message for insufficient permissions
            executor.getSender().sendMessage("You do not have permission to use this admin command: " + subCommand.getName());
            return true;
        }

        return subCommand.execute(executor.stripArgs(1));
    }

    private void addCommand(SubCommand<AdminCommand> command) {
        this.commands.put(command.getName(), command);
    }

    private Optional<SubCommand<AdminCommand>> getCommand(String name) {
        if (this.commands.containsKey(name)) {
            return Optional.of(this.commands.get(name));
        }
        return commands.values().stream().filter(c -> c.getName().equalsIgnoreCase(name) || c.getAliases().contains(name)).findFirst();
    }
}
