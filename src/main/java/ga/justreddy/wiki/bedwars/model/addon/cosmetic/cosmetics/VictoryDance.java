package ga.justreddy.wiki.bedwars.model.addon.cosmetic.cosmetics;

import ga.justreddy.wiki.bedwars.model.addon.cosmetic.Cosmetic;
import ga.justreddy.wiki.bedwars.model.addon.cosmetic.CosmeticRarity;
import ga.justreddy.wiki.bedwars.model.addon.cosmetic.CosmeticType;

/**
 * @author JustReddy
 */
public abstract class VictoryDance extends Cosmetic {

    public VictoryDance(int id, String displayName, int price, CosmeticRarity rarity) {
        super(id, displayName, price, rarity, CosmeticType.VICTORY_DANCE);
    }

    @Override
    public String getAddonName() {
        return "VictoryDance";
    }
}
