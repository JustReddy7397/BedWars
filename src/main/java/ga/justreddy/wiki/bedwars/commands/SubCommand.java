package ga.justreddy.wiki.bedwars.commands;


import ga.justreddy.wiki.bedwars.BedWars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JustReddy
 */
public abstract class SubCommand<T> {

    protected final BedWars plugin;
    private final String name;
    private final String description;
    private final String usage;
    private final String permission;
    private final List<String> aliases;

    public SubCommand(BedWars plugin, String name, String description, String usage, String permission, List<String> aliases) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.aliases = aliases;
    }

    public SubCommand(BedWars plugin, String name, String description, String usage, String permission, String... aliases) {
        this(plugin, name, description, usage, permission, new ArrayList<>(Arrays.asList(aliases)));
    }

    public SubCommand(BedWars plugin, String name, String description, String usage, String permission) {
        this(plugin, name, description, usage, permission, new ArrayList<>());
    }

    public SubCommand(BedWars plugin, String name, String description, String usage) {
        this(plugin, name, description, usage, null);
    }

    public SubCommand(BedWars plugin, String name, String description) {
        this(plugin, name, description, "", null);
    }

    public SubCommand(BedWars plugin, String name) {
        this(plugin, name, "", "", null);
    }

    public abstract boolean execute(Executor executor);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getAliases() {
        return aliases;
    }


}
