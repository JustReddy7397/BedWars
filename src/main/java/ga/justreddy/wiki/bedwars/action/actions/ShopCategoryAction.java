package ga.justreddy.wiki.bedwars.action.actions;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.action.Action;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;

/**
 * @author JustReddy
 */
public class ShopCategoryAction extends Action {
    @Override
    public String getId() {
        return "category";
    }

    @Override
    public void onAction(BedWars bedWars, GamePlayer gamePlayer, Object data) {
        String stringData = data instanceof String ? (String) data : null;
        if (stringData == null || stringData.isEmpty()) {
            bedWars.getLogger().warning("No category specified for ShopCategoryAction.");
            return;
        }


    }
}
