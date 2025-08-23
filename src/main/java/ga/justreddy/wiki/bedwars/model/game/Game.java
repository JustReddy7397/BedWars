package ga.justreddy.wiki.bedwars.model.game;

import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import ga.justreddy.wiki.bedwars.model.game.phases.PhaseManager;
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

    private final PhaseManager phaseManager;

    public Game(UUID uniqueId, FileConfiguration config) {
        this.uniqueId = uniqueId;
        this.config = config;
        this.name = config.getString("name");

        this.players = new ArrayList<>();
        this.phaseManager = new PhaseManager(this);
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

    public void initialize(World world) {

    }
}
