package ga.justreddy.wiki.bedwars.commands.bedwars.admin;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.commands.Executor;
import ga.justreddy.wiki.bedwars.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author JustReddy
 */
public class SpawnGeneratorsCommand extends SubCommand<AdminCommand> {

    public SpawnGeneratorsCommand(BedWars plugin) {
        super(plugin, "spawngenerators", "Spawn Generators", "/bw admin spawngenerators", null);
    }

    @Override
    public boolean execute(Executor executor) {

        System.out.println("Hi babies");

        final Player player = executor.getPlayer();
        final Location location = player.getLocation().clone();

        plugin.getGeneratorManager().getGenerators().forEach(gen -> {
            final Location loc = location.add(3, 0, 3);
            gen.cloneGenerator().spawn(loc.getWorld(), loc.clone());
            player.sendMessage("Spawned generator " + gen.getId() + " at " + loc);
        });

        return true;
    }
}
