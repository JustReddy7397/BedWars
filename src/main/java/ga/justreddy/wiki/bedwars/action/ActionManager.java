package ga.justreddy.wiki.bedwars.action;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.action.actions.GiveAction;
import ga.justreddy.wiki.bedwars.action.actions.ShopCategoryAction;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author JustReddy
 */
public class ActionManager {

    private static boolean initialized = false;

    private final Map<String, Action> actions;
    private final BedWars plugin;

    public ActionManager(BedWars plugin) {
        if (initialized) {
            throw new IllegalStateException("ActionManager is already initialized.");
        }
        this.plugin = plugin;
        this.actions = new HashMap<>();
        initialized = true;
        registerActions(new ShopCategoryAction(), new GiveAction());
    }

    private void registerActions(Action... actions) {
        for (final Action action : actions) {
            if (this.actions.containsKey(action.getId())) {
                throw new IllegalArgumentException("Action with ID '" + action.getId() + "' is already registered.");
            }
            this.actions.put(action.getId(), action);
        }
    }

    public void onAction(Player player, List<String> actions) {
        Optional<GamePlayer> optionalGamePlayer = GamePlayer.get(player);
        if (!optionalGamePlayer.isPresent()) {
            return;
        }

        final GamePlayer gamePlayer = optionalGamePlayer.get();

        onAction(gamePlayer, actions);
    }

    public void onAction(GamePlayer gamePlayer, List<String> actions) {
        actions.forEach(action -> {
            final String[] split = action.split(";");
            final String actionId = split[0];
            final Action actionObject = actionId == null ? null : this.actions.get(actionId.toLowerCase());

            if (actionObject == null) {
                plugin.getLogger().warning("Action with ID '" + actionId + "' not found.");
                return;
            }

            String data = action.length() > 1 ? action.substring(action.length() - 1) : "";
            // TODO PAPI

            actionObject.onAction(plugin, gamePlayer, data);
        });
    }

    public void onAction(GamePlayer gamePlayer, List<String> actions, Map<String, Boolean> conditions) {
        actions.forEach(action -> {
            final String[] split = action.split(";");
            final String actionId = split[0];
            if (conditions.containsKey(actionId) && !conditions.get(actionId)) {
                return;
            }
            final Action actionObject = actionId == null ? null : this.actions.get(actionId.toLowerCase());

            if (actionObject == null) {
                plugin.getLogger().warning("Action with ID '" + actionId + "' not found.");
                return;
            }

            String data = action.length() > 1 ? action.substring(action.length() - 1) : "";
            // TODO PAPI

            actionObject.onAction(plugin, gamePlayer, data);
        });
    }

    public void triggerAction(String actionId, GamePlayer gamePlayer, Object data) {
        Action action = this.actions.get(actionId.toLowerCase());
        if (action != null) {
            action.onAction(plugin, gamePlayer, data);
        } else {
            plugin.getLogger().warning("Action with ID '" + actionId + "' not found.");
        }
    }

}
