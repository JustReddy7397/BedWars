package ga.justreddy.wiki.bedwars.storage;


import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public interface Storage {

    void createTables();

    CompletableFuture<GamePlayer> loadPlayer(UUID uuid);

    CompletableFuture<GamePlayer> loadPlayer(String name);

}
