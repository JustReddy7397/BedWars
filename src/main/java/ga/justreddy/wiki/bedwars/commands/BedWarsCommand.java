package ga.justreddy.wiki.bedwars.commands;

import ga.justreddy.wiki.bedwars.BedWars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JustReddy
 */
public abstract class BedWarsCommand implements CommandExecutor {

    protected final BedWars plugin;
    protected final String name;
    private final boolean requiresPlayer;
    private final String permission;

    public BedWarsCommand(BedWars plugin, String name, boolean requiresPlayer, String permission, List<String> aliases) {
        this.name = name;
        this.plugin = plugin;
        this.requiresPlayer = requiresPlayer;
        this.permission = permission;
        final PluginCommand command = plugin.getCommand(name);
        command.setExecutor(this);
        if (aliases != null && !aliases.isEmpty()) {
            command.setAliases(aliases);
        }
    }

    public BedWarsCommand(BedWars plugin, String name, boolean requiresPlayer, String permission, String... aliases) {
        this(plugin, name, requiresPlayer, permission, new ArrayList<>(Arrays.asList(aliases)));
    }

    public BedWarsCommand(BedWars plugin, String name, boolean requiresPlayer, String permission) {
        this(plugin, name, requiresPlayer, permission, new ArrayList<>());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (permission != null && !sender.hasPermission(permission)) {
            // TODO -> send a message to the sender indicating they do not have permission
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        Player player = null;

        if (requiresPlayer && !(sender instanceof Player)) {
            // TODO -> send a message to the sender indicating they must be a player to use this command
            sender.sendMessage("This command can only be used by players.");
            return true;
        } else if (requiresPlayer) {
            player = (Player) sender;
        }

        return execute(new Executor(sender, player, args));
    }

    public abstract boolean execute(Executor executor);
}
