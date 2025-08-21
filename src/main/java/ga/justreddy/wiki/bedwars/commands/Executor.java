package ga.justreddy.wiki.bedwars.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author JustReddy
 */
public class Executor {

    private final CommandSender sender;
    private final Player player;
    private final String[] args;

    public Executor(CommandSender sender, Player player, String[] args) {
        this.sender = sender;
        this.player = player;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Player getPlayer() {
        return player;
    }

    public String[] getArgs() {
        return args;
    }

    public String getArg(int index) {
        if (index < 0 || index >= args.length) {
            return null;
        }
        return args[index];
    }

    public boolean hasArgs() {
        return args.length > 0;
    }

    public boolean hasArg(int index) {
        return index >= 0 && index < args.length;
    }

}
