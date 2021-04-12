package net.cherryflavor.api.spigot.world.generation;


/**
 * Created on 2/21/2021
 * Time 6:12 PM
 */
public enum WorldType {

    END("End"),
    NETHER("Nether"),
    FLAT("Flat"),
    NORMAL("Normal"),
    VOID("Void");

    String name;

    WorldType(String name) {
        this.name = name;
    }

    public static WorldType parse(String string) {
        for (WorldType type : values()) {
            if (type.name.equalsIgnoreCase(string)) {
                return type;
            }
        }
        return null;
    }

    public String getName() { return this.name; }

}
