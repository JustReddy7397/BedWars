package ga.justreddy.wiki.bedwars.model.entity;

import ga.justreddy.wiki.bedwars.model.entity.data.PlayerQuickBuy;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author JustReddy
 */
public class GamePlayer {

    private static final ConcurrentMap<UUID, GamePlayer> PLAYERS = new ConcurrentHashMap<>();

    private final UUID uniqueId;
    private final String name;
    private PlayerQuickBuy quickBuy;

    public GamePlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.quickBuy = new PlayerQuickBuy();
    }

    public static Optional<GamePlayer> get(UUID uniqueId) {
        return Optional.ofNullable(PLAYERS.get(uniqueId));
    }

    public static Optional<GamePlayer> get(Player player) {
        if (player == null) {
            return Optional.empty();
        }
        return get(player.getUniqueId());
    }

    public static Optional<GamePlayer> get(String name) {
        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }
        return PLAYERS.values().stream()
                .filter(gamePlayer -> gamePlayer.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public static void add(GamePlayer gamePlayer) {
        if (PLAYERS.containsKey(gamePlayer.getUniqueId())) {
            throw new IllegalArgumentException("GamePlayer with UUID '" + gamePlayer.getUniqueId() + "' is already registered.");
        }
        PLAYERS.put(gamePlayer.getUniqueId(), gamePlayer);
    }

    public static void remove(UUID uniqueId) {
        PLAYERS.remove(uniqueId);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public PlayerQuickBuy getQuickBuy() {
        return quickBuy;
    }
}
