package ga.justreddy.wiki.bedwars.model.game;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import ga.justreddy.wiki.bedwars.model.game.generator.Generator;
import ga.justreddy.wiki.bedwars.model.game.generator.GeneratorType;
import ga.justreddy.wiki.bedwars.model.game.phases.PhaseManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class Game {

    private final UUID uniqueId;
    private final FileConfiguration config;
    private final String name;

    private final List<GamePlayer> players;

    private final Table<String, Location, Generator> generators;

    private final PhaseManager phaseManager;

    public Game(UUID uniqueId, FileConfiguration config) {
        this.uniqueId = uniqueId;
        this.config = config;
        this.name = config.getString("name");

        this.players = new ArrayList<>();
        this.generators = HashBasedTable.create();
        this.phaseManager = new PhaseManager(this);
    }

    public void initialize(World world) {

    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    public Generator getGenerator(String id, Location location) {
        return generators.get(id, location);
    }

    public List<Generator> getGeneratorsById(String id) {
        return new ArrayList<>(generators.row(id).values());
    }

    public Table<String, Location, Generator> getGenerators() {
        return generators;
    }
}
