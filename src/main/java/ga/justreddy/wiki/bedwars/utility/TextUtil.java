package ga.justreddy.wiki.bedwars.utility;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class TextUtil {

    public static String colorize(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String colorize(String text, Object... args) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', String.format(text, args));
    }

    public static List<String> colorize(List<String> list) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }
        return list.stream()
                .map(TextUtil::colorize)
                .collect(Collectors.toList());
    }

    public static List<String> colorize(List<String> list, Object... args) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }
        return list.stream()
                .map(text -> colorize(text, args))
                .collect(Collectors.toList());
    }

}
