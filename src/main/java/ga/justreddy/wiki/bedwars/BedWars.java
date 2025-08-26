package ga.justreddy.wiki.bedwars;

import ga.justreddy.wiki.bedwars.action.ActionManager;
import ga.justreddy.wiki.bedwars.config.Config;
import ga.justreddy.wiki.bedwars.model.addon.AddonManager;
import ga.justreddy.wiki.bedwars.model.game.GameManager;
import ga.justreddy.wiki.bedwars.model.game.generator.GeneratorManager;
import ga.justreddy.wiki.bedwars.model.game.shop.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWars extends JavaPlugin {

    private ActionManager actionManager;
    private AddonManager addonManager;
    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private ShopManager shopManager;

    private Config settingsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadManagers() {
        this.actionManager = new ActionManager(this);
        this.addonManager = new AddonManager(this);
        this.gameManager = new GameManager(this);
        this.generatorManager = new GeneratorManager(this);
        this.shopManager = new ShopManager(this);
    }

    private void registerCommands() {
        // Register commands here
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public AddonManager getAddonManager() {
        return addonManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GeneratorManager getGeneratorManager() {
        return generatorManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public Config getSettingsConfig() {
        return settingsConfig;
    }
}
