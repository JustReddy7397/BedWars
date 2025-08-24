package ga.justreddy.wiki.bedwars.utility;

/**
 * Represents a replaceable object with a key and a value.
 * This class is used to store key-value pairs that can be replaced in strings.
 * @author JustReddy
 */
public class Replaceable {

    private final String key;
    private final String value;

    /**
     * Creates a new Replaceable object with the specified key and value.
     * @param key The key of this replaceable object.
     * @param value The value of this replaceable object.
     */
    public Replaceable(String key, Object value) {
        this.key = key;
        this.value = value.toString();
    }

    /**
     * Returns the key of this replaceable object.
     * @return The key of this replaceable object.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of this replaceable object.
     * @return The value of this replaceable object.
     */
    public String getValue() {
        return value;
    }
}
