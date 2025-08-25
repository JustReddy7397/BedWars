package ga.justreddy.wiki.bedwars.model.game;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.utility.ShuffleUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

/**
 * @author JustReddy
 */
public class GameManager {

    private static boolean initialized = false;

    private final Map<UUID, Game> games;
    private final File gamesDir;

    public GameManager(BedWars plugin) {
        if (initialized) {
            throw new IllegalStateException("GameManager is already initialized.");
        }
        this.games = new HashMap<>();
        this.gamesDir = new File(plugin.getDataFolder(), "data/games");
        if (!gamesDir.exists()) {
            gamesDir.mkdirs();
        }
        initialized = true;
        start(plugin);
    }

    public void start(BedWars plugin) {
        plugin.getLogger().log(Level.INFO, "Loading games...");
        final File[] files = gamesDir.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) {
            return;
        }

        for (final File file : files) {
            final String fileName = file.getName().replace(".yml", "");
            final UUID uniqueId = UUID.randomUUID();
            final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            final Game game = new Game(uniqueId, config);
            games.put(uniqueId, game);
            plugin.getLogger().log(Level.INFO, "Loaded game: " + fileName + " with ID: " + uniqueId);
        }
        plugin.getLogger().log(Level.INFO, "Loaded " + games.size() + " games.");
    }

    public List<Game> getGamesByName(String name) {
        List<Game> matchingGames = new ArrayList<>();
        for (final Game game : games.values()) {
            if (game.getName().equalsIgnoreCase(name)) {
                matchingGames.add(game);
            }
        }
        return matchingGames;
    }

    public Game getRandomGame() {
        if (games.isEmpty()) {
            return null;
        }

        if (games.size() == 1) {
            return games.values().iterator().next();
        }

        List<Game> games = new ArrayList<>(this.games.values());
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(1, 6); i++) {
            ShuffleUtil.shuffle(games);
        }

        return games.get(0);
    }

    public boolean doesGameWithNameExist(String name) {
        for (final Game game : games.values()) {
            if (game.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Map<UUID, Game> getGames() {
        return games;
    }
}
