package ga.justreddy.wiki.bedwars.commands.bedwars.admin;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.commands.Executor;
import ga.justreddy.wiki.bedwars.commands.SubCommand;

/**
 * @author JustReddy
 */
public class CreateCommand extends SubCommand<AdminCommand> {

    public CreateCommand(BedWars plugin) {
        super(plugin, "create", "Create a bedwars game", "Usage: /bw admin create <name>", "bedwars.admin.create");
    }

    @Override
    public boolean execute(Executor executor) {

        if (!executor.hasArgs()) {
            // TODO
            executor.getSender().sendMessage("Usage: " + getUsage());
            return true;
        }

        String gameName = executor.getArg(0);

        if (gameName.length() < 3 || gameName.length() > 16) {
            // TODO
            executor.getSender().sendMessage("Game name must be between 3 and 16 characters.");
            return true;
        }

        if (!gameName.matches("^[a-zA-Z0-9_]+$")) {
            // TODO
            executor.getSender().sendMessage("Game name can only contain letters, numbers, and underscores.");
            return true;
        }

        if (plugin.getGameManager().doesGameWithNameExist(gameName)) {
            // TODO
            executor.getSender().sendMessage("A game with that name already exists. You can copy it with /bw admin copy <name>.");
            return true;
        }

        // TODO OPEN GUI

        return true;
    }
}
